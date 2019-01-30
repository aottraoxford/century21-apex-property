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

        int a;
        List<Integer> b=new ArrayList<>();
        a=countryForWeb.size();
        for(int x=0;x<a;x++)
            b.add(countryForWeb.get(x).getProjectTypeForWebList().size());
        for(int i=0;i<a;i++) {
            Pagination pagination=new Pagination(page,limit);
            ProjectTypeForWeb projectTypeForWeb=new ProjectTypeForWeb();
            projectTypeForWeb.setType("all");
            projectTypeForWeb.setId(0);
            projectTypeForWeb.setProjectList(new ArrayList<>());
            countryForWeb.get(i).getProjectTypeForWebList().add(0,projectTypeForWeb);
            for (int j = 0; j < b.get(i); j++) {
                countryForWeb.get(i).getProjectTypeForWebList().get(j).setPagination(pagination);
            }
        }
        return countryForWeb;
    }
}
