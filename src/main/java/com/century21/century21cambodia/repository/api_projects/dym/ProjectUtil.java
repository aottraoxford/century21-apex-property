package com.century21.century21cambodia.repository.api_projects.dym;

import com.century21.century21cambodia.model.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ProjectUtil {
    public String getProject(@Param("cid") int countryID, @Param("pid") int projectTypeID, @Param("paging")Pagination pagination){
        return new SQL(){{
            SELECT("id,name,start_price,end_price,grr,country_id,project_type_id,thumbnail");
            FROM("project");
            WHERE("country_id=#{cid}");
            WHERE("isdisplay IS TRUE");
            if(projectTypeID>0)
                WHERE("project_type_id=#{pid}");
            ORDER_BY("id DESC LIMIT #{paging.limit} OFFSET #{paging.offset}");
        }}.toString();
    }

    public String countProject(@Param("cid") int countryID, @Param("pid") int projectTypeID){
        return new SQL(){{
            SELECT("COUNT(id)");
            FROM("project");
            WHERE("country_id=#{cid}");
            WHERE("isdisplay IS TRUE");
            if(projectTypeID>0){
                WHERE("project_type_id=#{pid}");
            }
        }}.toString();
    }
}
