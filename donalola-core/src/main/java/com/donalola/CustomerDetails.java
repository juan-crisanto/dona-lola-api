package com.donalola;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public final class CustomerDetails implements Serializable {

    private String name;
    private String email;
    private String phone;
}
