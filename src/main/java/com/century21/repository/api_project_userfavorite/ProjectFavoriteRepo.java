package com.century21.repository.api_project_userfavorite;

import com.century21.repository.api_project_userfavorite.dym.ProjectFavoriteUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectFavoriteRepo {
    @SelectProvider(type = ProjectFavoriteUtil.class,method = "startFavorite")
    void startFavorite(@Param("projectID") int projectID,@Param("propertyID")int propertyID, @Param("userID") int userID);

    @SelectProvider(type = ProjectFavoriteUtil.class,method = "endFavorite")
    void endFavorite(@Param("projectID") int projectID,@Param("propertyID")int propertyID, @Param("userID") int userID);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email = #{email}")
    Integer getUserIDByEmail(String email);

    @SelectProvider(type = ProjectFavoriteUtil.class,method = "isFavorite")
    int isFavorite(@Param("projectID") int projectID,@Param("propertyID")int propertyID, @Param("userID") int userID);

    class FavoriteOn{
        @JsonProperty("project_id")
        private int projectID;
        @JsonProperty("property_id")
        private int propertyID;

        public int getProjectID() {
            return projectID;
        }

        public void setProjectID(int projectID) {
            this.projectID = projectID;
        }

        public int getPropertyID() {
            return propertyID;
        }

        public void setPropertyID(int propertyID) {
            this.propertyID = propertyID;
        }
    }
}
