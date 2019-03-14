package com.century21.service;

import com.century21.model.Pagination;
import com.century21.repository.ProjectRepo;
import com.century21.repository.PropertyRepo;
import com.century21.repository.api_project_related.Project;
import org.apache.ibatis.annotations.Param;

import java.security.Principal;
import java.util.List;

public interface FavoriteService {
    boolean favorite(int projectID,int propertyID, Principal principal);
    List<PropertyRepo.Properties> propertyFavorite(Principal principal, Pagination pagination);
    List<ProjectRepo.ProjectListingResponse> projectFavorite(Principal principal, Pagination pagination);
}
