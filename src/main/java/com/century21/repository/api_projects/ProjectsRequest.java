package com.century21.repository.api_projects;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectsRequest {
    @JsonProperty("country_id")
    private int countryID;
    @JsonProperty("project_type_id")
    private int projectTypeID;
    @JsonProperty("is_enabled")
    private boolean isEnable;

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

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}
