package com.src.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogoutRequest {

    @JsonProperty(ATTR_USER_NAME)
    @NotBlank
    public String username;

    public static final String ATTR_USER_NAME = "username";

    public String getUsername() {
        return username;
    }
}
