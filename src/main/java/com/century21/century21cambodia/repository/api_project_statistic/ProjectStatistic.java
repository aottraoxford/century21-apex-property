package com.century21.century21cambodia.repository.api_project_statistic;

import java.util.List;

public class ProjectStatistic {
    private int totalProject;
    private List<Country> countries;

    public int getTotalProject() {
        return totalProject;
    }

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

