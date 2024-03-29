package com.src.models.assignment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUserAssignmentRequest {
    public static final String ATTR_USER_ID = "userid";
    @JsonProperty(ATTR_USER_ID)
    public long userid;

    public static final String ATTR_ASSIGNMENT = "activeAssignmentId";
    @JsonProperty(ATTR_ASSIGNMENT)
    public long activeAssignmentId;

    public long getUserid() {
        return userid;
    }

    public long getActiveAssignmentId() {
        return activeAssignmentId;
    }
}
