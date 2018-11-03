package com.donalola.util.tracking;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class TrackingIdGenerator {

    private final static long sequenceBits = 12;
    private final static long datacenterIdBits = 10L;
    private final static long twepoch = 1288834974657L;
    private final static long sequenceMax = 4096;
    private static final Logger logger = LoggerFactory.getLogger(TrackingIdGenerator.class);
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long datacenterIdShift = sequenceBits;
    private final long timestampLeftShift = sequenceBits + datacenterIdBits;
    private final long datacenterId;
    private volatile long lastTimestamp = -1L;
    private volatile long sequence = 0L;

    private TrackingIdGenerator() {
        datacenterId = getDatacenterId();
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            logger.error("datacenterId > maxDatacenterId");
        }
    }

    public static TrackingIdGenerator getInstance() {
        return Ref.basicEntityIdGenerator;
    }

    public synchronized String generateLongId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) + " milliseconds.");
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) % sequenceMax;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }
        lastTimestamp = timestamp;
        Long id = ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | sequence;
        return id.toString();
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }

    protected long getDatacenterId() {

        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            long id;
            if (network == null) {
                id = 1;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1]) | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
            }
            return id;
        } catch (Exception e) {
            logger.error("getDatacenterId", e);
            return 1;
        }
    }

    private static class Ref {

        static final TrackingIdGenerator basicEntityIdGenerator = new TrackingIdGenerator();
    }
}