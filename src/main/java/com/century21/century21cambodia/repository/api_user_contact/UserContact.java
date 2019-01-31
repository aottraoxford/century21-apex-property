package com.century21.century21cambodia.repository.api_user_contact;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;

public class UserContact {
    @JsonProperty("project_id")
    private int projectID;
    private String name;
    @Min(9)
    private String phone;
    private String email;

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
