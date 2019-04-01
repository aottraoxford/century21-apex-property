package com.century21.apexproperty.repository.api_user_favorite;

import com.century21.apexproperty.repository.api_project_related.Project;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFavoriteRepo {

    @Select("SELECT project.id,grr,project.name,start_price,end_price,country_id,project_type_id,thumbnail " +
            "FROM project " +
            "INNER JOIN favorite ON project.id=favorite.project_id " +
            "WHERE favorite.user_id=#{userID} " +
            "ORDER BY id DESC limit #{limit} offset #{offset}")
    @Results({
            @Result(property = "startPrice",column = "start_price"),
            @Result(property = "endPrice",column = "end_price"),
            @Result(property = "country",column = "country_id",one = @One(select = "projectCountry")),
            @Result(property = "projectType",column = "project_type_id",one = @One(select = "projectType"))
    })
    List<Project> favorites(@Param("userID") int userID, @Param("limit") int limit, @Param("offset") int offset);

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

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email = #{email}")
    Integer getUserIDByEmail(String email);

    @Select("SELECT count(project.id) " +
            "FROM project " +
            "INNER JOIN favorite ON project.id=favorite.project_id " +
            "WHERE favorite.user_id=#{userID} ")
    int countFavorites(@Param("userID") int userID);


}
