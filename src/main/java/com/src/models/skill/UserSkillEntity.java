package com.src.models.skill;

import com.src.models.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_skill_tab")
public class UserSkillEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_skill_id")
    private long userSkillId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private SkillEntity skill;

    @Column(name = "skill_experience")
    private  int skillExp;

    public long getUserSkillId() {
        return userSkillId;
    }

    public void setUserSkillId(long userSkillId) {
        this.userSkillId = userSkillId;
    }

    public int getSkillExp() {
        return skillExp;
    }

    public void setSkillExp(int skillExp) {
        this.skillExp = skillExp;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SkillEntity getSkill() {
        return skill;
    }

    public void setSkill(SkillEntity skill) {
        this.skill = skill;
    }
}
