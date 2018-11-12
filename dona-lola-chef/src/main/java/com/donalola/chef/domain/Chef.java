package com.donalola.chef.domain;

import com.donalola.ChefID;
import com.donalola.core.Location;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.security.Principal;

@Setter(AccessLevel.PRIVATE)
@Getter
public class Chef {

    private Chef(Builder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.name = builder.name;
        this.image = builder.image;
        this.openingSchedule = builder.openingSchedule;
        this.closingSchedule = builder.closingSchedule;
        this.location = builder.location;
    }

    private ChefID id;

    private String userId;

    private String name;

    private String image;

    private String openingSchedule;

    private String closingSchedule;

    private Location location;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Builder() {
        }

        private ChefID id;

        private String userId;

        private String name;

        private String image;

        private String openingSchedule;

        private String closingSchedule;

        private Location location;

        public Builder ChefID(ChefID chefID) {
            this.id = chefID;
            return this;
        }

        public Builder Name(String name) {
            if (StringUtils.isEmpty(name)) {
                throw new IllegalArgumentException("Chef name can't be null or empty");
            }
            this.name = name;
            return this;
        }

        public Builder OwnerId(String ownerId) {
            this.userId = ownerId;
            return this;
        }

        public Builder Owner(Principal principal) {
            if (principal == null) {
                throw new IllegalArgumentException("Owner of Chef can't be null");
            }
            this.userId = principal.getName();
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
            this.location = location;
            return this;
        }

        public Chef build() {
            return new Chef(this);
        }

    }

}
