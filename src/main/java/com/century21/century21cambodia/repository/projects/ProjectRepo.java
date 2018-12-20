package com.century21.century21cambodia.repository.projects;

import com.century21.century21cambodia.model.Pagination;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepo {
    @Select("SELECT id,name,start_price,end_price,grr,country_id,project_type_id,thumbnail " +
            "FROM project " +
            "WHERE country_id=#{countryID} AND project_type_id=#{typeID} " +
            "ORDER BY id " +
            "LIMIT #{paging.limit} OFFSET #{paging.offset}")
    @Results({
            @Result(property = "startPrice",column = "start_price"),
            @Result(property = "endPrice",column = "end_price"),
            @Result(property = "country",column = "country_id",one = @One(select = "projectCountry")),
            @Result(property = "projectType",column = "project_type_id",one = @One(select = "projectType"))
    })
    List<Project> projects(@Param("countryID")int countryID, @Param("typeID")int typeID, @Param("paging")Pagination pagination);
    @Select("SELECT name " +
            "FROM address " +
            "WHERE id=#{country_id}")
    String projectCountry();
    @Select("SELECT name " +
            "FROM project_type " +
            "WHERE id=#{project_type_id}")
    String projectType();

    @Select("SELECT count(id) " +
            "FROM project " +
            "WHERE country_id=#{countryID} AND project_type_id=#{typeID} ")
    int countProjects(@Param("countryID")int countryID,@Param("typeID")int typeID);

}

