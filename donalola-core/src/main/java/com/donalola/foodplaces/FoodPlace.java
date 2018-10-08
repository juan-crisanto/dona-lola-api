package com.donalola.foodplaces;

import com.donalola.core.Locale;
import lombok.Data;

import java.io.Serializable;

@Data
public class FoodPlace implements Serializable {

    private static final long serialVersionUID = -7611843508795842619L;

    private String id;
    private String name;
    private String photoUrl;

    private Locale locale;

}
