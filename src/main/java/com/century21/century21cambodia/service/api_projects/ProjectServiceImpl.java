package com.century21.century21cambodia.service.api_projects;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.model.Pagination;
import com.century21.century21cambodia.repository.api_projects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;
    @Override
    public List<Project> projects(int countryID, int projectTypeID, Pagination pagination) {
        projectRepo.findProject(countryID,projectTypeID,pagination);
        List<Project> projects = projectRepo.findProject(countryID,projectTypeID,pagination);
        if(projects==null || projects.size()<0){
            throw new CustomRuntimeException(404, "ZERO RESULT");
        }
        pagination.setTotalItem(projectRepo.countProject(countryID,projectTypeID));
        return projects;
    }

    @Override
    public List<CountryForWeb> getProjectsFroWeb(int page,int limit) {
        List<CountryForWeb> countryForWeb = projectRepo.getCountryForWeb();
        for(int i=0;i<countryForWeb.size();i++) {
            Pagination pagination=new Pagination(page,limit);
            for (int j = 0; j < countryForWeb.get(i).getProjectTypeForWebList().size(); j++) {
                countryForWeb.get(i).getProjectTypeForWebList().get(j).setPagination(pagination);
            }
        }
        return countryForWeb;
    }
}
