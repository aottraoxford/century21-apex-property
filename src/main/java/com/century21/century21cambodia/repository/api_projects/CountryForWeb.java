package com.century21.century21cambodia.repository.api_projects;

import java.util.List;

public class CountryForWeb {
    private int countryID;
    private String countryName;
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
