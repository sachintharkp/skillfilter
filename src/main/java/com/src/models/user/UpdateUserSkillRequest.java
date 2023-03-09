package com.src.models.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserSkillRequest {
    public static final String ATTR_USER_ID = "userid";
    @JsonProperty(ATTR_USER_ID)
    public long userid;

    public  static final String ATTR_SKILL_ID = "skillId";

    @JsonProperty(ATTR_SKILL_ID)
    public List<Long> skillId;

    public long getUserid() {
        return userid;
    }

    public List<Long> getSkillId() {
        return skillId;
    }
}
