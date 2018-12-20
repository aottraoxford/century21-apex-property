package com.century21.century21cambodia.service.projects;

import com.century21.century21cambodia.model.Pagination;
import com.century21.century21cambodia.repository.projects.Project;

import java.util.List;

public interface ProjectService {
    List<Project> projects(int countryID,int projectTypeID,Pagination pagination);
}
