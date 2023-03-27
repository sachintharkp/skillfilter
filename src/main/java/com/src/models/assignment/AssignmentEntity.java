package com.src.models.assignment;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "assignment_tab")
public class AssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assignment_id")
    private long assignmentId;
    @Column(name = "company_name")
    private String companyName;

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

    public List<UserAssignmentEntity> getAssignmentEntities() {
        return assignmentEntities;
    }

    public void setAssignmentEntities(List<UserAssignmentEntity> assignmentEntities) {
        this.assignmentEntities = assignmentEntities;
    }
}
