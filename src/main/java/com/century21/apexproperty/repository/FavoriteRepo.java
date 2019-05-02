package com.century21.apexproperty.repository;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Repository
public interface FavoriteRepo {
    @SelectProvider(type = FavoriteUtil.class,method = "projectFavoriteCount")
    int projectFavoriteCount(@Param("userID")int userID);

    @SelectProvider(type = FavoriteUtil.class,method = "propertyFavoriteCount")
    int propertyFavoriteCount(@Param("userID")int userID);

    @SelectProvider(type = FavoriteUtil.class,method = "projectFavorite")
    @Results({
            @Result(property = "status",column = "isdisplay"),
            @Result(property = "rentOrBuy",column = "rent_or_buy"),
            @Result(property = "country",column = "country_id",one = @One(select = "country")),
            @Result(property = "sqmPrice",column = "sqm_price"),
            @Result(property = "projectType",column = "project_type_id",one = @One(select = "projectType"))
    })
    List<ProjectRepo.ProjectListingResponse> projectFavorite(@Param("userID") int userID, @Param("limit") int limit, @Param("offset") int offset);

    @Select("SELECT name " +
            "FROM country " +
            "WHERE id=#{country_id}")
    String country();

    @Select("SELECT name " +
            "FROM project_type " +
            "WHERE id=#{project_type_id}")
    String projectType();

    @SelectProvider(type = FavoriteUtil.class,method = "propertyFavorite")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "sqmPrice",column = "sqm_price"),
            @Result(property = "rentOrBuy",column = "rent_or_sell"),
            @Result(property = "unitPrice",column = "unit_price"),
            @Result(property = "galleries",column = "id",many = @Many(select = "findGalleries"))
    })
    List<PropertyRepo.Properties> propertyFavorite(@Param("userID") int userID, @Param("limit") int limit, @Param("offset") int offset);

    @Select("SELECT id,name " +
            "FROM property_files " +
            "WHERE type = 'image' AND property_id=#{id}")
    @Results({
            @Result(property = "gallery",column = "name")
    })
    List<PropertyRepo.Gallery> findGalleries();

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
                    SELECT("project.id,grr,project.name,price,sqm_price,country_id,project_type_id,thumbnail,isdisplay,rent_or_buy,substring(description,1,200)||'.....' as description");
                    FROM("project");
                    INNER_JOIN("favorite ON project.id=favorite.project_id");
                    WHERE("favorite.user_id=#{userID}");
                    ORDER_BY("favorite.id DESC limit #{limit} offset #{offset}");
                }
            }.toString();
        }

        public String projectFavoriteCount(@Param("userID") int userID){
            return new SQL(){
                {
                    SELECT("count(project.id)");
                    FROM("project");
                    INNER_JOIN("favorite ON project.id=favorite.project_id");
                    WHERE("favorite.user_id=#{userID}");
                }
            }.toString();
        }

        public String propertyFavorite(@Param("userID") int userID, @Param("limit") int limit, @Param("offset") int offset){
            return new SQL(){
                {
                    SELECT("lat,lng,property.id,property.title,property.unit_price,property.sqm_price,property.country,property.type,property.status,rent_or_sell");
                    FROM("property");
                    INNER_JOIN("favorite ON property.id=favorite.property_id");
                    WHERE("favorite.user_id=#{userID}");
                    ORDER_BY("favorite.id DESC limit #{limit} offset #{offset}");
                }
            }.toString();
        }

        public String propertyFavoriteCount(@Param("userID") int userID){
            return new SQL(){
                {
                    SELECT("count(property.id)");
                    FROM("property");
                    INNER_JOIN("favorite ON property.id=favorite.property_id");
                    WHERE("favorite.user_id=#{userID}");
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
