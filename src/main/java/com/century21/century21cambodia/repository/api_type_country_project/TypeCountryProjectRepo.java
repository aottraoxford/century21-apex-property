package com.century21.century21cambodia.repository.api_type_country_project;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeCountryProjectRepo {
    @Select("SELECT DISTINCT country.name,country.id " +
            "FROM project " +
            "INNER JOIN country ON project.country_id=country.id " +
            "WHERE isdisplay IS TRUE")
    @Results({
            @Result(property = "countryID",column = "id"),
            @Result(property = "countryName",column = "name"),
            @Result(property = "types",column = "id",many = @Many(select = "findProjectTypes"))
    })
    List<TypeCountryProject> typeCP();

    @Select("SELECT DISTINCT project_type.name,project_type.id " +
            "FROM project_type " +
            "INNER JOIN project ON project.project_type_id=project_type.id " +
            "WHERE project.country_id=#{id}" )
    @Results({
            @Result(property = "projectID",column = "id"),
            @Result(property = "typeName",column = "name")
    })
    List<ProjectType> findProjectTypes();


}
