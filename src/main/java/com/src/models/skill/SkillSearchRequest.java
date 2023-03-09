package com.src.models.skill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillSearchRequest {

    public  static final String ATTR_SKILL_ID = "skillId";

    @JsonProperty(ATTR_SKILL_ID)
    public List<Long> skillId;

    @JsonProperty(ATTR_EXPERIENCE)
    public int years;

    public static final String ATTR_EXPERIENCE = "experience";

    public List<Long> getSkillId() {
        return skillId;
    }

    public int getYears() {
        return years;
    }

}
