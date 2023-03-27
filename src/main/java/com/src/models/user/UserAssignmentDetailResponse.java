package com.src.models.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAssignmentDetailResponse {


    public static final String ATTR_ASSIGNMENT = "activeAssignment";
    @JsonProperty(ATTR_ASSIGNMENT)
    public long activeAssignmentId;
    public static final String ATTR_ASSIGNMENT_COMPANY = "companyName";

    @JsonProperty(ATTR_ASSIGNMENT_COMPANY)
    public String companyName;


    public static final String ATTR_ACTIVE_STATUS = "Status";
    @JsonProperty(ATTR_ACTIVE_STATUS)
    public boolean isActive;

    public void setActiveAssignmentId(long activeAssignmentId) {
        this.activeAssignmentId = activeAssignmentId;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
