package com.century21.century21cambodia.repository.api_projects;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Positive;

public class ProjectsRequest {
    @Positive
    @JsonProperty("country_id")
    private int countryID;
    @Positive
    @JsonProperty("project_type_id")
    private int projectTypeID;

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getProjectTypeID() {
        return projectTypeID;
    }

    public void setProjectTypeID(int projectTypeID) {
        this.projectTypeID = projectTypeID;
    }
}
