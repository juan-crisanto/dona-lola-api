package com.donalola.api.authentication.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JsonUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

    @JsonCreator
    public JsonUsernamePasswordAuthenticationToken(@JsonProperty("principal") Object principal, @JsonProperty("credentials") Object credentials) {
        super(principal, credentials);
    }
}
