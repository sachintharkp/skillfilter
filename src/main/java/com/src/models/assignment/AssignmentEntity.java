package com.src.models.assignment;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "assignment_tab")
public class AssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assignments_id")
    private long assignmentId;
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "position")
    private String position;

    @OneToMany(mappedBy = "assignment")
    List<UserAssignmentEntity> assignmentEntities;

    public long getAssignmentId() {
        return assignmentId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public List<UserAssignmentEntity> getAssignmentEntities() {
        return assignmentEntities;
    }

    public void setAssignmentEntities(List<UserAssignmentEntity> assignmentEntities) {
        this.assignmentEntities = assignmentEntities;
    }
}
