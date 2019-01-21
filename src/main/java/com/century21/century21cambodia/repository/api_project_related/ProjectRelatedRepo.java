package com.century21.century21cambodia.repository.api_project_related;

import com.century21.century21cambodia.repository.api_project_related.dym.ProjectRelatedUtil;
import com.century21.century21cambodia.repository.api_projects.dym.ProjectUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRelatedRepo {
    @SelectProvider(type= ProjectRelatedUtil.class,method = "getProjectsRelated")
    @Results({
            @Result(property = "startPrice",column = "start_price"),
            @Result(property = "endPrice",column = "end_price"),
            @Result(property = "country",column = "country_id",one = @One(select = "projectCountry")),
            @Result(property = "projectType",column = "project_type_id",one = @One(select = "projectType"))
    })
    List<Project> projectsRelated(@Param("cid")int cid,@Param("pid")int pid);

    @Select("SELECT name " +
            "FROM country " +
            "WHERE id=#{country_id} " +
            "ORDER BY id")
    String projectCountry();

    @Select("SELECT name " +
            "FROM project_type " +
            "WHERE id=#{project_type_id} " +
            "ORDER BY id")
    String projectType();
}
