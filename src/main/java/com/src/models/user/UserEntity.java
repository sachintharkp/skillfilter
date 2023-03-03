package com.src.models.user;

import com.src.models.skill.SkillEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_util")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private long userId;
    @Column(name = "user_name")
    @NotNull
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    @NotNull
    private String firstname;

    @Column(name = "last_name")
    @NotNull
    private String lastname;

    @Column(name = "experience")
    @NotNull
    private int years;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "user_skill_tab",
            joinColumns = {@JoinColumn(name = "user_util_userId")},
            inverseJoinColumns = {@JoinColumn(name = "skill_util_skillId")})


    private List<SkillEntity> skillEntities = new ArrayList<>();

    public long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }
    public List<SkillEntity> getSkills() {
        return skillEntities;
    }

    public void setSkills(List<SkillEntity> skillEntities) {
        this.skillEntities = skillEntities;
    }

}
