package com.src.models.skill;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SkillRequest {

    public static final String ATTR_SKILL_SDESC = "skillName";
    @JsonProperty(ATTR_SKILL_SDESC)
    @NotBlank
    public String skillSdesc;

    public static final String ATTR_SKILL_LDESC = "skillDescription";
    @JsonProperty(ATTR_SKILL_LDESC)
    public String skillLdesc;

    public String getSkillSdesc() {
        return skillSdesc;
    }

    public String getSkillLdesc() {
        return skillLdesc;
    }

    public void setSkillSdesc(String skillSdesc) {
        this.skillSdesc = skillSdesc;
    }

    public void setSkillLdesc(String skillLdesc) {
        this.skillLdesc = skillLdesc;
    }
}
