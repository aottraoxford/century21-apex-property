package com.century21.century21cambodia.service.api_project_related;

import com.century21.century21cambodia.repository.api_project_related.Project;

import java.util.List;

public interface ProjectRelatedService {
    List<Project> getProjects(int countryID, int projectTypeID);
}
