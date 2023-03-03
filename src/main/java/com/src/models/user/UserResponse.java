package com.src.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserResponse {

    public static final String ATTR_USER_ID = "userid";
    @JsonProperty(ATTR_USER_ID)
    public long userid;

    public static final String ATTR_USER_NAME = "username";

    @JsonProperty(ATTR_USER_NAME)
    public String username;

    public static final String ATTR_PASSWORD = "password";

    @JsonProperty(ATTR_PASSWORD)
    public String password;


    public static final String ATTR_FIRST_NAME = "firstName";
    @JsonProperty(ATTR_FIRST_NAME)
    public String firstname;


    public static final String ATTR_LAST_NAME = "lastName";
    @JsonProperty(ATTR_LAST_NAME)
    public String lastname;


    public static final String ATTR_EXPERIENCE = "experience";
    @JsonProperty(ATTR_EXPERIENCE)
    public String years;



    public void setUserid(long userid) {
        this.userid = userid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setYears(String years) {
        this.years = years;
    }
}