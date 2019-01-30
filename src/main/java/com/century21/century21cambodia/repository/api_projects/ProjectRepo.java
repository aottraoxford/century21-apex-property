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

    @Select("SELECT DISTINCT(country.name),country.id " +
            "FROM country " +
            "INNER JOIN project ON project.country_id=country.id " +
            "WHERE project.isdisplay IS TRUE " +
            "ORDER BY country.id ")
    @Results({
            @Result(property = "projectTypeForWebList",column = "id",many = @Many(select = "getProjectTypeForWeb")),
            @Result(property = "countryName",column = "name"),
            @Result(property = "countryID",column = "id")
    })
    List<CountryForWeb> getCountryForWeb();

    @Select("SELECT DISTINCT(project_type.name),project_type.id ,project.country_id " +
            "FROM project_type " +
            "INNER JOIN project ON project.project_type_id=project_type.id " +
            "WHERE project.country_id = #{id} " +
            "ORDER BY project_type.id")
    @Results({
            //@Result(property = "projectList",column = "{cid=country_id,pid=id}",many = @Many(select = "getProjectForWeb")),
            @Result(property = "type",column = "name")
    })
    List<ProjectTypeForWeb> getProjectTypeForWeb();

    @Select("SELECT id,name,start_price,end_price,grr,country_id,project_type_id,thumbnail " +
            "FROM project " +
            "WHERE country_id=#{cid} AND project_type_id=#{pid} " +
            "ORDER BY id DESC LIMIT 1")
    List<Project> getProjectForWeb();


}

