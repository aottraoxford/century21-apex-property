package com.century21.service.api_new_project;

import com.century21.repository.api_new_project.Country;
import com.century21.repository.api_new_project.Project;

import java.util.List;

public interface NewProjectService {
    int createNewProject(Project project);
    List<Country> countries(String name);
}
