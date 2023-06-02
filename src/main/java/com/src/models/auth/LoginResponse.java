package com.src.models.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {

    public static final String ATTR_USER_ID = "userid";

    @JsonProperty(ATTR_USER_ID)
    public long userid;

    public static final String ATTR_AUTHENTICATED = "authtoken";

    @JsonProperty(ATTR_AUTHENTICATED)
    public UUID authToken;

    public static final String ATTR_STATUS = "status";
    @JsonProperty(ATTR_STATUS)
    public String status;

    public static final String ATTR_ROLE = "role";

    @JsonProperty(ATTR_ROLE)
    public String role;



    public void setUserid(long userid) {
        this.userid = userid;
    }

    public void setAuthToken(UUID authToken) {
        this.authToken = authToken;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
