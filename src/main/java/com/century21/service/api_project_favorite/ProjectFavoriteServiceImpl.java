package com.century21.service.api_project_favorite;

import com.century21.exception.CustomRuntimeException;
import com.century21.repository.api_project_userfavorite.ProjectFavoriteRepo;
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
