package com.century21.repository.search;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepo {
    @Select("SELECT DISTINCT(country.id),country.name " +
            "FROM project INNER JOIN country ON country.id=project.country_id " +
            "WHERE project.isdisplay IS true")
    List<Countries> countries();

    @Select("SELECT DISTINCT(project_type.id),project_type.name " +
            "FROM project INNER JOIN project_type ON project_type.id=project.project_type_id " +
            "WHERE project.isdisplay IS true")
    List<ProjectType> projectTypes();

    @Select(value= "{CALL search(#{searchParam.title},#{searchParam.rentOrBuy},#{searchParam.sort},#{searchParam.city},#{searchParam.projectTypeID},#{searchParam.countryID},#{searchParam.roomAmount},#{searchParam.startPrice},#{searchParam.endPrice},#{limit},#{offset})}")
    @Results({
            @Result(property = "id",column = "re_id"),
            @Result(property = "name",column = "re_name"),
            @Result(property = "grr",column="re_grr"),
            @Result(property = "country",column = "re_country"),
            @Result(property = "endPrice",column = "re_end_price"),
            @Result(property = "startPrice",column = "re_start_price"),
            @Result(property = "projectType",column = "re_project_type"),
            @Result(property = "thumbnail",column = "re_thumbnail")
    })
    @Options(statementType = StatementType.CALLABLE)
    List<Project> search(@Param("searchParam") SearchParam searchParam, @Param("limit") int limit, @Param("offset") int offset);

    @Select(value= "{CALL count_search(#{searchParam.title},#{searchParam.rentOrBuy},#{searchParam.city},#{searchParam.projectTypeID},#{searchParam.countryID},#{searchParam.roomAmount},#{searchParam.startPrice},#{searchParam.endPrice})}")
    @Options(statementType = StatementType.CALLABLE)
    int countSearch(@Param("searchParam") SearchParam searchParam);
}
