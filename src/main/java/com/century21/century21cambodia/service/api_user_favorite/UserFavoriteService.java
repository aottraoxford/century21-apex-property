package com.century21.century21cambodia.service.api_user_favorite;

import com.century21.century21cambodia.model.Pagination;
import com.century21.century21cambodia.repository.api_projects.Project;

import java.security.Principal;
import java.util.List;

public interface UserFavoriteService {
    List<Project> favorite(Principal principal, Pagination pagination);
}
