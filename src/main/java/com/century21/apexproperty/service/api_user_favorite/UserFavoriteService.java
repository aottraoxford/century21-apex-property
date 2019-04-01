package com.century21.apexproperty.service.api_user_favorite;

import com.century21.apexproperty.model.Pagination;
import com.century21.apexproperty.repository.api_project_related.Project;

import java.security.Principal;
import java.util.List;

public interface UserFavoriteService {
    List<Project> favorite(Principal principal, Pagination pagination);
}
