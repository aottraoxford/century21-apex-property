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
    public boolean favorite(int projectID,int propertyID, Principal principal) {
        if((projectID==0 && propertyID==0) || (projectID!=0 && propertyID!=0)) throw new CustomRuntimeException(400,"projectID or propertyID ,one of must be = 0.");
        Integer userID=projectFavoriteRepo.getUserIDByEmail(principal.getName());
        if(userID==null) throw new CustomRuntimeException(404,"USER INVALID");
        int isFavorite= projectFavoriteRepo.isFavorite(projectID,propertyID,userID);
        if(isFavorite==0) {
            projectFavoriteRepo.startFavorite(projectID,propertyID, userID);
            return true;
        }
        else {
            projectFavoriteRepo.endFavorite(projectID,propertyID, userID);
            return false;
        }
    }
}
