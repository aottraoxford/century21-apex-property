package com.century21.repository.api_type_country_project;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

public class ProjectType {
    @ApiModelProperty(position = 1)
    @JsonProperty("id")
    private int projectID;
    @ApiModelProperty (position = 2)
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
