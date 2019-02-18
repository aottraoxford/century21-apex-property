package com.century21.service.api_project_favorite;

import java.security.Principal;

public interface ProjectFavoriteService {
    boolean favorite(int projectID, Principal principal);
}
