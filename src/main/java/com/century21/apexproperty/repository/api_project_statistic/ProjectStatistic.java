package com.century21.apexproperty.repository.api_project_statistic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProjectStatistic {
    @JsonProperty("total_project")
    private int totalProject;
    private List<Country> countries;


    public void setTotalProject(int totalProject) {
        this.totalProject = totalProject;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public void setCountries(List<Country> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "ProjectStatistic{" +
                "totalProject=" + totalProject +
                ", countries=" + countries +
                '}';
    }
}

