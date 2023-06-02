package com.src.models.auth;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "hist_login")
public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "hist_id")
    private long histId;


    @Column(name = "user_id")
    private long userId;

    @Column(name = "token")
    private String token;


    @Column(name = "LastLogged")
    @Temporal(TemporalType.TIMESTAMP)
    private Date LastLogged;

    @Column(name = "active")
    private boolean active;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastLogged() {
        return LastLogged;
    }

    public void setLastLogged(Date lastLogged) {
        this.LastLogged = lastLogged;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
