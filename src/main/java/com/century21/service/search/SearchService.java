package com.century21.service.search;

import com.century21.model.Pagination;
import com.century21.repository.search.Countries;
import com.century21.repository.search.Project;
import com.century21.repository.search.ProjectType;
import com.century21.repository.search.SearchParam;

import java.util.List;

public interface SearchService {
    List<Project> search(SearchParam searchParam, Pagination pagination);
    List<ProjectType> projectTypes();
    List<Countries> countries();
}
