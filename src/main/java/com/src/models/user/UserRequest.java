package com.src.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {

    @JsonProperty(ATTR_USER_NAME)
    @NotBlank
    public String username;

    public static final String ATTR_USER_NAME = "username";

    @JsonProperty(ATTR_PASSWORD)
    public String password;

    public static final String ATTR_PASSWORD = "password";

    @JsonProperty(ATTR_FIRST_NAME)
    @NotBlank
    public String firstName;

    public static final String ATTR_FIRST_NAME = "firstName";

    @JsonProperty(ATTR_LAST_NAME)
    @NotBlank
    public String lastName;

    public static final String ATTR_LAST_NAME = "lastName";

    @JsonProperty(ATTR_EXPERIENCE)
    public int years;

    public static final String ATTR_EXPERIENCE = "experience";

    @JsonProperty(ATTR_SKILL)
    public List<Long> skillList;
    public static final String ATTR_SKILL = "skill";

    public String getUsername() {
        return username;
    }


    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getYears() {
        return years;
    }

    public List<Long> getSkillList() {
        return skillList;
    }
}
