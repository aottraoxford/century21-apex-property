package com.century21.repository.api_project_userfavorite.dym;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ProjectFavoriteUtil {
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
