package com.century21.repository;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepo {
    @SelectProvider(type = FavoriteUtil.class,method = "projectFavorite")
    List<ProjectRepo.ProjectListingResponse> projectFavorite(@Param("userID") int userID, @Param("limit") int limit, @Param("offset") int offset);


    @SelectProvider(type = FavoriteUtil.class,method = "propertyFavorite")
    List<PropertyRepo.Properties> propertyFavorite(@Param("userID") int userID, @Param("limit") int limit, @Param("offset") int offset);


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

        public String projectFavorite(@Param("userID") int userID, @Param("limit") int limit, @Param("offset") int offset){
            return new SQL(){
                {
                    SELECT("project.id,grr,project.name,start_price,end_price,country_id,project_type_id,thumbnail");
                    FROM("project");
                    INNER_JOIN("favorite ON project.id=favorite.project_id");
                    WHERE("favorite.user_id=#{userID}");
                    ORDER_BY("id DESC limit #{limit} offset #{offset}");
                }
            }.toString();
        }

        public String propertyFavorite(@Param("userID") int userID, @Param("limit") int limit, @Param("offset") int offset){
            return new SQL(){
                {
                    SELECT("property.id,property.title,property.unit_price,property.sqm_price,property.country,property.type,property.status");
                    FROM("property");
                    INNER_JOIN("favorite ON property.id=favorite.property_id");
                    WHERE("favorite.user_id=#{userID}");
                    ORDER_BY("id DESC limit #{limit} offset #{offset}");
                }
            }.toString();
        }

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
