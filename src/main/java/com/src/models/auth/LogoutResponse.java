package com.src.models.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LogoutResponse {

    @JsonProperty(ATTR_STATUS)
    public String status;

    public static final String ATTR_STATUS = "status";

    public void setStatus(String status) {
        this.status = status;
    }
}
