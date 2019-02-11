package com.century21.century21cambodia.service.api_project_favorite;

import java.security.Principal;

public interface ProjectFavoriteService {
    boolean favorite(int projectID, Principal principal);
}
