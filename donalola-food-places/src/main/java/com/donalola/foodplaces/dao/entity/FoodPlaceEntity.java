package com.donalola.foodplaces.dao.entity;

import com.donalola.core.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "food-place")
public class FoodPlaceEntity extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String photoUrl;
    private String district;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Long latitude;
    @Column(nullable = false)
    private Long longitude;


}
