package com.century21.century21cambodia.repository.api_project_statistic;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectStatisticRepo {

    @Select("SELECT count(id) " +
            "FROM project")
    int totalProject();

    @Select("SELECT distinct(country_id) " +
            "FROM project")
    @Results({
            @Result(property = "total",column = "country_id",many = @Many(select = "totalCountry")),
            @Result(property = "name",column = "country_id",many = @Many(select = "countryName")),
            @Result(property = "types",column = "country_id",many = @Many(select = "projectTypes"))
    })
    List<Country> countries();

    @Select("SELECT count(id) " +
            "FROM project " +
            "WHERE country_id=#{country_id}")
    int totalCountry();

    @Select("SELECT name " +
            "FROM country " +
            "WHERE id=#{country_id}")
    String countryName();

    @Select("SELECT distinct(project_type_id) " +
            "FROM project " +
            "WHERE country_id=#{country_id}")
    @Results({
            @Result(property = "type",column = "project_type_id",many = @Many(select = "type")),
            @Result(property = "total",column = "id",one = @One(select = "totalProjectTypes"))
    })
    List<Type> projectTypes();

    @Select("SELECT name " +
            "FROM project_type " +
            "WHERE id=#{project_type_id}")
    String type();

    @Select("SELECT count(id) " +
            "FROM project " +
            "WHERE project_type_id=#{id}")
    int totalProjectTypes();


}