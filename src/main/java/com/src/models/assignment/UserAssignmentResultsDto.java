package com.src.models.assignment;


public class UserAssignmentResultsDto {

    private long assignmentId;

    private String companyName;

    private String position;

    private boolean isActive;

    public UserAssignmentResultsDto(long assignmentId, String companyName, String position, boolean isActive) {
        this.assignmentId = assignmentId;
        this.companyName = companyName;
        this.position = position;
        this.isActive = isActive;
    }

    public long getAssignmentId() {
        return assignmentId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getPosition() {
        return position;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
