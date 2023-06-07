package com.src.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogoutRequest {

    @JsonProperty(ATTR_USER_ID)
    public long userid;

    public static final String ATTR_USER_ID = "userid";

    public long getUserid() {
        return userid;
    }
}
