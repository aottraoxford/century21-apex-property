package com.century21.century21cambodia.repository.api_type_country_project;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectType {
    @JsonProperty("project_id")
    private int projectID;
    @JsonProperty("type")
    private String typeName;

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
