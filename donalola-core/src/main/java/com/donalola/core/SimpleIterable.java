package com.donalola.core;

import java.util.Iterator;
import java.util.Optional;

public class SimpleIterable<T> implements Iterable<T> {

    private Iterator<T> iterator;

    public SimpleIterable(Iterator<T> iterator) {
        if (!Optional.ofNullable(iterator).isPresent()) {
            throw new IllegalArgumentException("Iterator must not be null");
        }
        this.iterator = iterator;
    }

    @Override
    public Iterator<T> iterator() {
        return this.iterator;
    }
}
