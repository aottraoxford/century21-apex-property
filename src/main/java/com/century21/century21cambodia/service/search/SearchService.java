package com.century21.century21cambodia.service.search;

import com.century21.century21cambodia.model.Pagination;
import com.century21.century21cambodia.repository.search.*;

import java.util.List;

public interface SearchService {
    List<Project> search(SearchParam searchParam, Pagination pagination);
    List<ProjectType> projectTypes();
    List<Countries> countries();
}
