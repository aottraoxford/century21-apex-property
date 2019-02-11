package com.century21.century21cambodia.service.api_user_favorite;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.model.Pagination;
import com.century21.century21cambodia.repository.api_projects.Project;
import com.century21.century21cambodia.repository.api_user_favorite.UserFavoriteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class UserFavoriteServiceImpl implements UserFavoriteService {
    @Autowired
    private UserFavoriteRepo userFavoriteRepo;

    @Override
    public List<Project> favorite(Principal principal, Pagination pagination) {
        Integer userID=userFavoriteRepo.getUserIDByEmail(principal.getName());
        if(userID==null) throw new CustomRuntimeException(404,"email not valid.");
        List<Project> projects=userFavoriteRepo.favorites(userID,pagination.getLimit(),pagination.getOffset());
        if(projects==null || projects.size()<1) throw new CustomRuntimeException(404,"ZERO RESULT");
        pagination.setTotalItem(userFavoriteRepo.countFavorites(userID));
        return projects;
    }
}
