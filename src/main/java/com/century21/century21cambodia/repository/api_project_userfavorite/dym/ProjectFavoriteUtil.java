package com.century21.century21cambodia.repository.api_project_userfavorite.dym;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ProjectFavoriteUtil {
    public String startFavorite(@Param("projectID")int projectID,@Param("userID")int userID){
        return new SQL(){
            {
                INSERT_INTO("favorite");
                VALUES("project_id,user_id","#{projectID},#{userID}");
            }
        }.toString();
    }
    public String endFavorite(@Param("projectID")int projectID,@Param("userID")int userID){
        return new SQL(){
            {
                DELETE_FROM("favorite");
                WHERE("project_id=#{projectID}");
                WHERE("user_id=#{userID}");
            }
        }.toString();
    }
}
