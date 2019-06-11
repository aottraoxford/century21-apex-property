package com.century21.apexproperty.repository.api_project_related;

import com.century21.apexproperty.repository.api_project_related.dym.ProjectRelatedUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRelatedRepo {
    @SelectProvider(type = ProjectRelatedUtil.class, method = "getProjectsRelated")
    @Results({
            @Result(property = "status", column = "isdisplay"),
            @Result(property = "rentOrBuy", column = "rent_or_buy"),
            @Result(property = "country", column = "country_id", one = @One(select = "projectCountry")),
            @Result(property = "projectType", column = "project_type_id", one = @One(select = "projectType"))
    })
    List<Project> projectsRelated(@Param("cid") int cid, @Param("pid") int pid);

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
