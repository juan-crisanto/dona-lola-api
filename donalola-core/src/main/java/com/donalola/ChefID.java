package com.donalola;

import org.apache.commons.lang3.StringUtils;

public final class ChefID extends IDValueObject<String> {

    private ChefID(String value) {
        super(value);
    }

    public static ChefID of(String value) {
        if (StringUtils.isEmpty(value)) {
            throw new IllegalArgumentException("ChefID value can't be null!");
        }
        return new ChefID(value);
    }
}
