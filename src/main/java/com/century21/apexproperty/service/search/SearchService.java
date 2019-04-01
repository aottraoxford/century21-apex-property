package com.century21.apexproperty.service.search;

import com.century21.apexproperty.model.Pagination;
import com.century21.apexproperty.repository.search.Countries;
import com.century21.apexproperty.repository.search.Project;
import com.century21.apexproperty.repository.search.ProjectType;
import com.century21.apexproperty.repository.search.SearchParam;

import java.util.List;

public interface SearchService {
    List<Project> search(SearchParam searchParam, Pagination pagination);
    List<ProjectType> projectTypes();
    List<Countries> countries();
}
