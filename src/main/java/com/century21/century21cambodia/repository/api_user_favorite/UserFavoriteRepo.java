package com.century21.century21cambodia.repository.api_user_favorite;

import com.century21.century21cambodia.repository.api_projects.Project;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteRepo {

    @Select("SELECT id,grr,name,start_price,end_price")
    List<Project> favorites(int userID);
}
