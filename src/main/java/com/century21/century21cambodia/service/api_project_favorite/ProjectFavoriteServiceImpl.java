package com.century21.century21cambodia.service.api_project_favorite;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_project_userfavorite.ProjectFavoriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ProjectFavoriteServiceImpl implements ProjectFavoriteService {
    @Autowired
    private ProjectFavoriteRepo projectFavoriteRepo;
    @Override
    public boolean favorite(int projectID, Principal principal) {
        Integer userID=projectFavoriteRepo.getUserIDByEmail(principal.getName());
        if(userID==null) throw new CustomRuntimeException(404,"USER INVALID");
        Integer isfavorite= projectFavoriteRepo.isFavorite(projectID,userID);
        if(isfavorite==null) {
            projectFavoriteRepo.startFavorite(projectID, userID);
            return true;
        }
        else {
            projectFavoriteRepo.endFavorite(projectID, userID);
            return false;
        }
    }
}
