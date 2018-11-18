package com.donalola;

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
