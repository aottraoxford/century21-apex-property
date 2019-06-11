package com.century21.apexproperty.service.api_project_statistic;

import com.century21.apexproperty.repository.api_project_statistic.Event;
import com.century21.apexproperty.repository.api_project_statistic.ProjectStatistic;
import com.century21.apexproperty.repository.api_project_statistic.ProjectStatisticRepo;
import com.century21.apexproperty.repository.api_project_statistic.Statistic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectStatisticServiceImpl implements ProjectStatisticService {
    @Autowired
    private ProjectStatisticRepo projectStatisticRepo;

    @Override
    public Statistic statistic() {
        Event event = new Event();
        event.setTotalDisable(projectStatisticRepo.eventDisable());
        event.setTotalEnable(projectStatisticRepo.eventEnable());

        ProjectStatistic projectStatistic = new ProjectStatistic();
        projectStatistic.setCountries(projectStatisticRepo.countries());
        projectStatistic.setTotalProject(projectStatisticRepo.totalProject());

        Statistic statistic = new Statistic();
        statistic.setEvent(event);
        statistic.setProjectStatistic(projectStatistic);
        return statistic;
    }
}
