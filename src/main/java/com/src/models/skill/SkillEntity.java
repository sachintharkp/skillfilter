package com.src.models.skill;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
@Table(name="skill_util")
public class SkillEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "skill_id")
    private long skillId;
    @Column(name = "skill_sdesc")
    @NotNull
    private String skillSdesc;

    @Column(name = "skill_ldesc")
    private String skillLdesc;

    @OneToMany(mappedBy = "skill")
    List<UserSkillEntity> skillEntities;


    public long getSkillId() {
        return skillId;
    }

    public String getSkillSdesc() {
        return skillSdesc;
    }

    public void setSkillSdesc(String skillSdesc) {
        this.skillSdesc = skillSdesc;
    }

    public String getSkillLdesc() {
        return skillLdesc;
    }

    public void setSkillLdesc(String skillLdesc) {
        this.skillLdesc = skillLdesc;
    }

    public List<UserSkillEntity> getSkillEntities() {
        return skillEntities;
    }

    public void setSkillEntities(List<UserSkillEntity> skillEntities) {
        this.skillEntities = skillEntities;
    }
}
