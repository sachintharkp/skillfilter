package com.src.models.assignment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AddAssignmentRequest {
    public static final String ATTR_ASSIGNMENT_COMPANY = "companyName";

    @JsonProperty(ATTR_ASSIGNMENT_COMPANY)
    @NotBlank
    public String companyName;

    public static final String ATTR_ASSIGNMENT_POSITION = "position";

    @JsonProperty(ATTR_ASSIGNMENT_POSITION)
    @NotBlank
    public String position;

    public static final String ATTR_ASSIGNMENT_SEATS= "noSeats";

    @JsonProperty(ATTR_ASSIGNMENT_SEATS)
    public int seats;


    public String getCompanyName() {
        return companyName;
    }

    public String getPosition() {
        return position;
    }
    public int getSeats() {
        return seats;
    }
}
