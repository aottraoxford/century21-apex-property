package com.century21.service.api_user_favorite;

import com.century21.model.Pagination;
import com.century21.repository.api_project_related.Project;

import java.security.Principal;
import java.util.List;

public interface UserFavoriteService {
    List<Project> favorite(Principal principal, Pagination pagination);
}
