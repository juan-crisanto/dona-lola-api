package com.donalola.foodplaces.dao.entity;

import com.donalola.core.dao.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = "FOOD_PLACE")
public class FoodPlaceEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -7656802625497475155L;
    @Id
    @Column(name = "ID", updatable = false, insertable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "PHOTO_URL")
    private String photoUrl;
    @Column(name = "DISTRICT")
    private String district;
    @Column(name = "ADDRESS", nullable = false)
    private String address;
    @Column(name = "LATITUDE", nullable = false)
    private String latitude;
    @Column(name = "LONGITUDE", nullable = false)
    private String longitude;


}
