package com.century21.apexproperty.repository.api_user_contact;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;

public class UserContact {
    @JsonProperty("project_id")
    private Integer projectID;
    @JsonProperty("property_id")
    private Integer propertyID;
    private String name;
    private String phone;
    private String email;

    public Integer getProjectID() {
        return projectID;
    }

    public void setProjectID(Integer projectID) {
        this.projectID = projectID;
    }

    public Integer getPropertyID() {
        return propertyID;
    }

    public void setPropertyID(Integer propertyID) {
        this.propertyID = propertyID;
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
