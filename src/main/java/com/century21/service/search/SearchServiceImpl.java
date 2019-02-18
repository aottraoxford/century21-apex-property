package com.century21.service.search;

import com.century21.exception.CustomRuntimeException;
import com.century21.model.Pagination;
import com.century21.repository.search.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private SearchRepo searchRepo;
    @Override
    public List<Project> search(SearchParam searchParam, Pagination pagination) {
        List<Project> projects=searchRepo.search(searchParam,pagination.getLimit(),pagination.getOffset());
        if(projects.size()<1){
            throw new CustomRuntimeException(404,"ZERO RECORD");
        }
        pagination.setTotalItem(searchRepo.countSearch(searchParam));
        return projects;
    }

    @Override
    public List<ProjectType> projectTypes() {
        List<ProjectType> projectTypes=searchRepo.projectTypes();
        if(projectTypes.size()<1)
            throw new CustomRuntimeException(404,"ZERO RESULT");
        return projectTypes;
    }

    @Override
    public List<Countries> countries() {
        List<Countries> countries = searchRepo.countries();
        if(countries.size()<1) throw new CustomRuntimeException(404,"ZERO RESULT");
        return searchRepo.countries();
    }
}
