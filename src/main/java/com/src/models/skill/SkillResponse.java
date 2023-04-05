package com.src.models.skill;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SkillResponse {

    public static final String ATTR_SKILL_ID = "skillId";

    @JsonProperty(ATTR_SKILL_ID)
    public long skillId;

    public static final String ATTR_SKILL_SDESC = "skillName";
    @JsonProperty(ATTR_SKILL_SDESC)
    public String skillSdesc;

    public static final String ATTR_SKILL_LDESC = "skillDescription";
    @JsonProperty(ATTR_SKILL_LDESC)
    public String skillLdesc;

    public void setSkillId(long skillId) {
        this.skillId = skillId;
    }

    public void setSkillSdesc(String skillSdesc) {
        this.skillSdesc = skillSdesc;
    }

    public void setSkillLdesc(String skillLdesc) {
        this.skillLdesc = skillLdesc;
    }

    public long getSkillId() {
        return skillId;
    }

    public String getSkillSdesc() {
        return skillSdesc;
    }

    public String getSkillLdesc() {
        return skillLdesc;
    }
}
