package com.century21.century21cambodia.repository.api_type_country_project;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class TypeCountryProject {
    @ApiModelProperty(position = 1)
    @JsonProperty("id")
    private int countryID;
    @ApiModelProperty (position = 2)
    @JsonProperty("name")
    private String countryName;
    @ApiModelProperty (position = 3)
    private List<ProjectType> types;

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<ProjectType> getTypes() {
        return types;
    }

    public void setTypes(List<ProjectType> types) {
        this.types = types;
    }
}
