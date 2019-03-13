package com.century21.repository;

import com.century21.repository.api_project_userfavorite.dym.ProjectFavoriteUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepo {
    @SelectProvider(type = FavoriteUtil.class,method = "startFavorite")
    void startFavorite(@Param("projectID") int projectID, @Param("propertyID")int propertyID, @Param("userID") int userID);

    @SelectProvider(type = FavoriteUtil.class,method = "endFavorite")
    void endFavorite(@Param("projectID") int projectID,@Param("propertyID")int propertyID, @Param("userID") int userID);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email = #{email}")
    Integer getUserIDByEmail(String email);

    @SelectProvider(type = FavoriteUtil.class,method = "isFavorite")
    int isFavorite(@Param("projectID") int projectID,@Param("propertyID")int propertyID, @Param("userID") int userID);

    class FavoriteUtil{
        public String startFavorite(@Param("projectID")int projectID,@Param("propertyID")int propertyID,@Param("userID")int userID){
            return new SQL(){
                {
                    INSERT_INTO("favorite");
                    if(projectID!=0){
                        VALUES("project_id,user_id","#{projectID},#{userID}");
                    }else if(propertyID!=0){
                        VALUES("property_id,user_id","#{propertyID},#{userID}");
                    }
                }
            }.toString();
        }
        public String endFavorite(@Param("projectID")int projectID,@Param("propertyID")int propertyID,@Param("userID")int userID){
            return new SQL(){
                {
                    DELETE_FROM("favorite");
                    if(projectID!=0) {
                        WHERE("project_id=#{projectID}");
                    }else if(propertyID!=0) {
                        WHERE("property_id=#{propertyID}");
                    }
                    WHERE("user_id=#{userID}");
                }
            }.toString();
        }

        public String isFavorite(@Param("projectID") int projectID,@Param("propertyID")int propertyID, @Param("userID") int userID){
            return new SQL(){
                {
                    SELECT("count(id)");
                    FROM("favorite");
                    if(projectID!=0)
                        WHERE("project_id=#{projectID}");
                    else if(propertyID!=0)
                        WHERE("property_id=#{propertyID}");
                    WHERE("user_id=#{userID}");
                }
            }.toString();
        }
    }

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
