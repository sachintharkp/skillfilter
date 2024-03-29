package com.src.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAssignmentDetailResponse {

    public static final String ATTR_ASSIGNMENT_UNIQUE = "uniqueAssignmentId";
    @JsonProperty(ATTR_ASSIGNMENT_UNIQUE)
    public long uniqueAssignmentId;

    public static final String ATTR_ASSIGNMENT = "activeAssignmentId";
    @JsonProperty(ATTR_ASSIGNMENT)
    public long activeAssignmentId;
    public static final String ATTR_ASSIGNMENT_COMPANY = "companyName";
    @JsonProperty(ATTR_ASSIGNMENT_COMPANY)
    public String companyName;


    public static final String ATTR_ASSIGNMENT_POSITION = "position";
    @JsonProperty(ATTR_ASSIGNMENT_POSITION)
    public String position;

    public static final String ATTR_ASSIGNMENT_SEATS= "noSeats";

    @JsonProperty(ATTR_ASSIGNMENT_SEATS)
    public int seats;


    public static final String ATTR_ASSIGNMENT_STATUS = "status";
    @JsonProperty(ATTR_ASSIGNMENT_STATUS)
    public boolean status;


    public void setActiveAssignmentId(long activeAssignmentId) {
        this.activeAssignmentId = activeAssignmentId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUniqueAssignmentId(long uniqueAssignmentId) {
        this.uniqueAssignmentId = uniqueAssignmentId;
    }
}
