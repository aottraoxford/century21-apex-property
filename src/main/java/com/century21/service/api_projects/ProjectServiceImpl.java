package com.century21.service.api_projects;

import com.century21.exception.CustomRuntimeException;
import com.century21.model.Pagination;
import com.century21.repository.api_projects.CountryForWeb;
import com.century21.repository.api_projects.Project;
import com.century21.repository.api_projects.ProjectRepo;
import com.century21.repository.api_projects.ProjectTypeForWeb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;
    @Override
    public List<Project> projects(int countryID, int projectTypeID, boolean status, Pagination pagination) {
        List<Project> projects = projectRepo.findProject(countryID,projectTypeID,status,pagination);
        if(projects==null || projects.size()<1){
            throw new CustomRuntimeException(404, "ZERO RESULT");
        }
        pagination.setTotalItem(projectRepo.countProject(countryID,projectTypeID,status));
        return projects;
    }

    @Override
    public List<CountryForWeb> getProjectsFroWeb(int page, int limit) {
        List<CountryForWeb> countryForWeb = projectRepo.getCountryForWeb();
        for(int i=0;i<countryForWeb.size();i++){
            for(int j=0;j<countryForWeb.get(i).getProjectTypeForWebList().size();j++){
                countryForWeb.get(i).getProjectTypeForWebList().get(j).setProjectList(new ArrayList<>());
                countryForWeb.get(i).getProjectTypeForWebList().get(j).setPagination(new Pagination(1,10));
                if(j+1==countryForWeb.get(i).getProjectTypeForWebList().size()){
                    ProjectTypeForWeb projectTypeForWeb=new ProjectTypeForWeb();
                    projectTypeForWeb.setProjectList(new ArrayList<>());
                    projectTypeForWeb.setPagination(new Pagination(1,10));
                    projectTypeForWeb.setId(0);
                    projectTypeForWeb.setType("all");
                    countryForWeb.get(i).getProjectTypeForWebList().add(0,projectTypeForWeb);
                    break;
                }
            }
        }
        return countryForWeb;
    }

    @Override
    public List<Project> listAllProject(String title, Boolean status, Pagination pagination) {
        if(title!=null){
            title=title.trim();
            title=title.replaceAll(" ","%");
            title="%"+title+"%";
        }
        List<Project> projects = projectRepo.listAllProject(title,status,pagination.getLimit(),pagination.getOffset());
        if(projects==null || projects.size()<1) throw new CustomRuntimeException(404,"ZERO RESULT");
        pagination.setTotalItem( projectRepo.countListAllProject(title,status));
        return projects;
    }
}
