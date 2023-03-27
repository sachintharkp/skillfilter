package com.src.models.assignment;


public class UserAssignmentResultsDto {

    private long id;

    private String name;


    private String position;

    private  int status;

    public UserAssignmentResultsDto(long id) {
        this.id = id;
        //this.name = name;
        //this.position = position;
        //this.status = status;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
