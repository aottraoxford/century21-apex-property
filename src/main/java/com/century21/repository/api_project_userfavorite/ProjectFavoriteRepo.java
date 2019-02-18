package com.century21.repository.api_project_userfavorite;

import com.century21.repository.api_project_userfavorite.dym.ProjectFavoriteUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectFavoriteRepo {
    @SelectProvider(type = ProjectFavoriteUtil.class,method = "startFavorite")
    void startFavorite(@Param("projectID") int projectID, @Param("userID") int userID);

    @SelectProvider(type = ProjectFavoriteUtil.class,method = "endFavorite")
    void endFavorite(@Param("projectID") int projectID, @Param("userID") int userID);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email = #{email}")
    Integer getUserIDByEmail(String email);

    @Select("SELECT id " +
            "FROM favorite " +
            "WHERE project_id=#{projectID} AND user_id=#{userID}")
    Integer isFavorite(@Param("projectID") int projectID, @Param("userID") int userID);
}
