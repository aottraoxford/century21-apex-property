package com.century21.century21cambodia.repository.api_projects;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CountryForWeb {
    @JsonProperty("country_id")
    private int countryID;
    @JsonProperty("country_name")
    private String countryName;
    @JsonProperty("project_type")
    private List<ProjectTypeForWeb> projectTypeForWebList;

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

    public List<ProjectTypeForWeb> getProjectTypeForWebList() {
        return projectTypeForWebList;
    }

    public void setProjectTypeForWebList(List<ProjectTypeForWeb> projectTypeForWebList) {
        this.projectTypeForWebList = projectTypeForWebList;
    }

    @Override
    public String toString() {
        return "CountryForWeb{" +
                "countryID=" + countryID +
                ", countryName='" + countryName + '\'' +
                ", projectTypeForWebList=" + projectTypeForWebList +
                '}';
    }
}
