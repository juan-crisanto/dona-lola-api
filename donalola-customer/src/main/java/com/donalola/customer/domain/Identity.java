package com.donalola.customer.domain;

import lombok.*;

@Value(staticConstructor = "of")
@RequiredArgsConstructor(staticName = "of")
@Setter(AccessLevel.PRIVATE)
@Getter
public class Identity {

    public enum Type {
        DNI
    }

    private String value;
    private Type type;
}
