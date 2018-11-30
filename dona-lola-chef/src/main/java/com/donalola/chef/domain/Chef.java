package com.donalola.chef.domain;

import com.donalola.ChefID;
import com.donalola.Geolocated;
import com.donalola.Identity;
import com.donalola.core.Location;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Setter(AccessLevel.PRIVATE)
@Getter
public class Chef implements Geolocated {

    private Chef(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.image = builder.image;
        this.openingSchedule = builder.openingSchedule;
        this.closingSchedule = builder.closingSchedule;
        this.location = builder.location;
        this.identity = builder.identity;
        this.phone = builder.phone;
        this.attentionTypes = builder.attentionTypes;
    }

    private ChefID id;

    private String name;

    private String image;

    private String openingSchedule;

    private String closingSchedule;

    private Location location;

    private Identity identity;

    private String phone;

    private List<AttentionType> attentionTypes;

    public enum AttentionType {
        DELIVER, PICK_UP
    }

    public static Builder builder(ChefID chefID) {
        return new Builder(chefID);
    }

    @Override
    public double getLatitude() {
        return this.location.getLatitude();
    }

    @Override
    public double getLongitude() {
        return this.location.getLongitude();
    }

    public static class Builder {

        private ChefID id;

        private String name;

        private String image;

        private String openingSchedule;

        private String closingSchedule;

        private Location location;

        private Identity identity;

        private String phone;

        private List<AttentionType> attentionTypes;

        private Builder(ChefID chefID) {
            this.id = chefID;
        }

        public Builder Name(String name) {
            if (StringUtils.isEmpty(name)) {
                throw new IllegalArgumentException("Chef name can't be null or empty");
            }
            this.name = name;
            return this;
        }

        public Builder Base64Image(String image) {
            this.image = image;
            return this;
        }

        public Builder OpeningOn(String openingSchedule) {
            this.openingSchedule = openingSchedule;
            return this;
        }

        public Builder ClosingOn(String closingSchedule) {
            this.closingSchedule = closingSchedule;
            return this;
        }

        public Builder Location(Location location) {
            if (location == null) {
                throw new IllegalArgumentException("Location of a Chef can't be null");
            }
            this.location = location;
            return this;
        }

        public Builder Identity(Identity.Type type, String value) {
            this.identity = Identity.of(value, type);
            return this;
        }

        public Builder Phone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder AttentionTypes(List<AttentionType> attentionTypes) {
            if (CollectionUtils.isEmpty(attentionTypes)) {
                throw new IllegalArgumentException("Must specify at least an attention type");
            }
            this.attentionTypes = attentionTypes;
            return this;
        }

        public Chef build() {
            if (CollectionUtils.isEmpty(this.attentionTypes)) {
                throw new IllegalArgumentException("Must specify at least an attention type");
            }
            return new Chef(this);
        }

    }

}
