package com.century21.century21cambodia.service.projects;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.model.Pagination;
import com.century21.century21cambodia.repository.projects.Project;
import com.century21.century21cambodia.repository.projects.ProjectRepo;
import com.century21.century21cambodia.repository.projects.ProjectType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Currency;
import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepo projectRepo;
    @Override
    public List<Project> projects(int countryID, int projectTypeID, Pagination pagination) {
        List<Project> projects=projectRepo.projects(countryID,projectTypeID,pagination);
        if(projects.size()<1){
            throw new CustomRuntimeException(404,"ZERO RESULT");
        }
        pagination.setTotalItem(projectRepo.countProjects(countryID,projectTypeID));
        return projects;
    }
}
