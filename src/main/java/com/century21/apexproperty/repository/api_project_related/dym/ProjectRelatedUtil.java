package com.century21.apexproperty.repository.api_project_related.dym;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class ProjectRelatedUtil {
    public String getProjectsRelated(@Param("cid") int countryID, @Param("pid") int projectTypeID){
        return new SQL(){{
            SELECT("substring(project.description,1,200)||'.....' as description,rent_or_buy,project.id,project.name,price,grr,country_id,project_type_id,country.name,project_type.name,thumbnail,isdisplay");
            FROM("project");
            WHERE("country_id=#{cid}");
            WHERE("isdisplay IS TRUE");
            if(projectTypeID>0)
                WHERE("project_type_id=#{pid}");
            ORDER_BY("RANDOM() LIMIT 6");
        }}.toString();
    }
}
