package com.century21.service;

import com.century21.exception.CustomRuntimeException;
import com.century21.repository.FavoriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class FavoriteServiceImpl implements FavoriteService{
    @Autowired
    private FavoriteRepo favoriteRepo;
    @Override
    public boolean favorite(int projectID, int propertyID, Principal principal) {
        if((projectID==0 && propertyID==0) || (projectID!=0 && propertyID!=0)) throw new CustomRuntimeException(400,"projectID or propertyID ,one of must be = 0.");
        Integer userID=favoriteRepo.getUserIDByEmail(principal.getName());
        if(userID==null) throw new CustomRuntimeException(404,"USER INVALID");
        int isFavorite= favoriteRepo.isFavorite(projectID,propertyID,userID);
        if(isFavorite==0) {
            favoriteRepo.startFavorite(projectID,propertyID, userID);
            return true;
        }
        else {
            favoriteRepo.endFavorite(projectID,propertyID, userID);
            return false;
        }
    }
}
