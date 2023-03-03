package com.src.models.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.src.models.user.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

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

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "skillEntities")
    @JsonIgnore
    private Set<UserEntity> userEntities = new HashSet<>();

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

    public Set<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(Set<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }
}
