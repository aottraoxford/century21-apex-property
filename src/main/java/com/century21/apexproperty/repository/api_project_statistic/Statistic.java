package com.century21.apexproperty.repository.api_project_statistic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Statistic {
    @JsonProperty("project")
    private ProjectStatistic projectStatistic;
    private Event event;

    public ProjectStatistic getProjectStatistic() {
        return projectStatistic;
    }

    public void setProjectStatistic(ProjectStatistic projectStatistic) {
        this.projectStatistic = projectStatistic;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "projectStatistic=" + projectStatistic +
                ", event=" + event +
                '}';
    }
}
