package com.century21.century21cambodia.repository.api_projects;

import com.century21.century21cambodia.model.Pagination;
import com.century21.century21cambodia.repository.api_projects.dym.ProjectUtil;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo {

    @SelectProvider(type= ProjectUtil.class,method = "getProject")
    @Results({
            @Result(property = "startPrice",column = "start_price"),
            @Result(property = "endPrice",column = "end_price"),
            @Result(property = "country",column = "country_id",one = @One(select = "projectCountry")),
            @Result(property = "projectType",column = "project_type_id",one = @One(select = "projectType"))
    })
    List<Project> findProject(@Param("cid")int cid,@Param("pid")int pid,@Param("paging")Pagination pagination);

    @SelectProvider(type = ProjectUtil.class,method = "countProject")
    Integer countProject(@Param("cid")int cid,@Param("pid")int pid);

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

