package com.donalola.commons.dynamodb.util;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DynamoDBConverter {

    static public class LocalDateConverter implements DynamoDBTypeConverter<String, LocalDate> {

        @Override
        public String convert(LocalDate localDate) {
            return localDate.toString();
        }

        @Override
        public LocalDate unconvert(String strLocalDate) {
            return LocalDate.parse(strLocalDate);
        }
    }

    static public class LocalDateTimeConverter implements DynamoDBTypeConverter<String, LocalDateTime> {

        @Override
        public String convert(LocalDateTime localDateTime) {
            return localDateTime.toString();
        }

        @Override
        public LocalDateTime unconvert(String strLocaleDateTime) {
            return LocalDateTime.parse(strLocaleDateTime);
        }
    }

    static public class BigDecimalConverter implements DynamoDBTypeConverter<String, BigDecimal> {
        @Override
        public String convert(BigDecimal bigDecimal) {
            return bigDecimal.toString();
        }

        @Override
        public BigDecimal unconvert(String strBigDecimal) {
            return NumberUtils.createBigDecimal(strBigDecimal);
        }
    }
}
