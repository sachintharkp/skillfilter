package com.src.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserProfileRequest {

    public static final String ATTR_USER_ID = "userid";
    @JsonProperty(ATTR_USER_ID)
    public long userid;

    @JsonProperty(ATTR_EXPERIENCE)
    public int years;
    public static final String ATTR_EXPERIENCE = "experience";

    @JsonProperty(ATTR_SKILL_ID)
    public List<Long> skillId;

    public  static final String ATTR_SKILL_ID = "skillId";

    @JsonProperty(ATTR_ASSIGNMENT_ID)
    public long activeAssignmentId;
    public static final String ATTR_ASSIGNMENT_ID = "activeAssignmentId";

    @JsonProperty(ATTR_ASSIGNMENT_STATUS)
    public String assignmentStatus;
    public static final String ATTR_ASSIGNMENT_STATUS = "assignmentstatus";


    public long getUserid() {
        return userid;
    }

    public int getYears() {
        return years;
    }

   public List<Long> getSkillId() {
        return skillId;
    }

    public long getActiveAssignmentId() {
        return activeAssignmentId;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }
}
