package com.century21.service;

import java.security.Principal;

public interface FavoriteService {
    boolean favorite(int projectID,int propertyID, Principal principal);
}
