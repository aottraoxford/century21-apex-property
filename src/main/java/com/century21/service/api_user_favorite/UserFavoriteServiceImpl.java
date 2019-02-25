package com.century21.service.api_user_favorite;

import com.century21.exception.CustomRuntimeException;
import com.century21.model.Pagination;
import com.century21.repository.api_project_related.Project;
import com.century21.repository.api_user_favorite.UserFavoriteRepo;
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
