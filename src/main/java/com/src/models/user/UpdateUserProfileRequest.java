package com.src.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserProfileRequest {

    public static final String ATTR_USER_ID = "userid";
    @JsonProperty(ATTR_USER_ID)
    public long userid;

    @JsonProperty(ATTR_FIRST_NAME)
    public String firstName;
    public static final String ATTR_FIRST_NAME = "firstName";

    @JsonProperty(ATTR_LAST_NAME)
    public String lastName;
    public static final String ATTR_LAST_NAME = "lastName";

    @JsonProperty(ATTR_EXPERIENCE)
    public int years;
    public static final String ATTR_EXPERIENCE = "experience";

    public static final String ATTR_USER_NAME = "username";
    @JsonProperty(ATTR_USER_NAME)
    public String username;

    public  static final String ATTR_SKILL_ID = "skillId";

    @JsonProperty(ATTR_SKILL_ID)
    public List<Long> skillId;

    public static final String ATTR_ASSIGNMENT_ID = "activeAssignmentId";
    @JsonProperty(ATTR_ASSIGNMENT_ID)
    public long activeAssignmentId;

    public long getUserid() {
        return userid;
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

    public String getUsername() {
        return username;
    }

    public List<Long> getSkillId() {
        return skillId;
    }

    public long getActiveAssignmentId() {
        return activeAssignmentId;
    }
}
