package com.century21.apexproperty.service;

import com.century21.apexproperty.model.Pagination;
import com.century21.apexproperty.repository.ProjectRepo;
import com.century21.apexproperty.repository.PropertyRepo;

import java.security.Principal;
import java.util.List;

public interface FavoriteService {
    boolean favorite(int projectID, int propertyID, Principal principal);

    List<PropertyRepo.Properties> propertyFavorite(Principal principal, Pagination pagination);

    List<ProjectRepo.ProjectListingResponse> projectFavorite(Principal principal, Pagination pagination);
}
