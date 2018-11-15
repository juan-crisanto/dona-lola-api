package com.donalola;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

@Value(staticConstructor = "of")
@RequiredArgsConstructor(staticName = "of")
@Getter
@Setter
public final class CustomerDetails implements Serializable {

    private String name;
    private String email;
    private String phone;
}
