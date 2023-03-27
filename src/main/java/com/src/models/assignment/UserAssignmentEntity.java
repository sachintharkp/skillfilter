package com.src.models.assignment;

import com.src.models.user.UserEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "user_assignment_tab")
public class UserAssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_assignment_id")
    private long userAssignmentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private AssignmentEntity assignment;

    @Column(name = "is_active")
    private  boolean isActive;

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public AssignmentEntity getAssignment() {
        return assignment;
    }

    public void setAssignment(AssignmentEntity assignment) {
        this.assignment = assignment;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public long getUserAssignmentId() {
        return userAssignmentId;
    }

    public void setUserAssignmentId(long userAssignmentId) {
        this.userAssignmentId = userAssignmentId;
    }
}
