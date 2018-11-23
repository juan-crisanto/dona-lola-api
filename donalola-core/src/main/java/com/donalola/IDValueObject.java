package com.donalola;

import lombok.Getter;

import java.io.Serializable;

public abstract class IDValueObject<T> implements Serializable {

    @Getter
    T value;

    public IDValueObject(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o.getClass().isAssignableFrom(this.getClass()))) {
            return false;
        }

        T objectValue = ((IDValueObject<T>) o).getValue();

        if (!objectValue.equals(this.getValue())) {
            return false;
        }

        return true;
    }
}
