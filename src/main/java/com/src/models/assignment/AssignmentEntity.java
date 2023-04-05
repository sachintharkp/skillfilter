package com.src.models.assignment;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "assignment_tab")
@SqlResultSetMapping(
        name="userAssignmentDetailsMapping",
        classes={
                @ConstructorResult(
                        targetClass=UserAssignmentResultsDto.class,
                        columns={
                                @ColumnResult(name="assignmentId"),
                                @ColumnResult(name="companyName"),
                                @ColumnResult(name="position"),
                                @ColumnResult(name="isActive"),
                        }
                )
        }
)

@NamedNativeQuery(
        name="AssignmentEntity.getUserAssignmentDetails",
        query=("SELECT\n" +
                "        a.assignments_id AS 'assignmentId', \n" +
                "        a.company_name AS 'companyName', \n" +
                "        a.position AS 'position', \n" +
                "        u.is_active AS 'isActive' \n" +
                "        FROM user_assignment_tab u\n" +
                "        INNER JOIN assignment_tab a ON u.assignment_id = a.assignments_id\n" +
                "        WHERE u.user_id = :userId "),
        resultSetMapping="userAssignmentDetailsMapping"
)

public class AssignmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "assignments_id")
    private long assignmentId;
    @Column(name = "company_name")
    private String companyName;

    @Column(name = "position")
    private String position;

    @Column(name = "no_seats")
    private int seats;

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

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public List<UserAssignmentEntity> getAssignmentEntities() {
        return assignmentEntities;
    }

    public void setAssignmentEntities(List<UserAssignmentEntity> assignmentEntities) {
        this.assignmentEntities = assignmentEntities;
    }
}
