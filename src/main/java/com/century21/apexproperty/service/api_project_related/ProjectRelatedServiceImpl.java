package com.century21.apexproperty.service.api_project_related;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_project_related.Project;
import com.century21.apexproperty.repository.api_project_related.ProjectRelatedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectRelatedServiceImpl implements ProjectRelatedService {
    @Autowired
    private ProjectRelatedRepo projectRelatedRepo;

    @Override
    public List<Project> getProjects(int countryID, int projectTypeID) {
        List<Project> relatedProjects = projectRelatedRepo.projectsRelated(countryID, projectTypeID);
        if (relatedProjects.size() < 1)
            throw new CustomRuntimeException(404, "ZERO RESULT");
        return relatedProjects;
    }
}
