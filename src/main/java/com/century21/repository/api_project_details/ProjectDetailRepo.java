package com.century21.repository.api_project_details;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectDetailRepo {
    @Select("SELECT project.* FROM project " +
            "WHERE project.id=#{projectID}")
    @Results(value = {
            @Result(property = "builtDate",column = "built_date"),
            @Result(property = "completedDate",column = "completed_date"),
            @Result(property = "title",column = "name"),
            @Result(property = "addressOne",column = "address_1"),
            @Result(property = "addressTwo",column = "address_2"),
            @Result(property = "minPrice",column = "start_price"),
            @Result(property = "maxPrice",column = "end_price"),
            @Result(property = "averageAnnualRentFrom",column = "avg_rent_from"),
            @Result(property = "averageAnnualRentTo",column = "avg_rent_to"),
            @Result(property = "downPayment",column = "down_payment"),
            @Result(property = "country",column = "country_id",one = @One(select = "country")),
            @Result(property = "projectType",column = "project_type_id",one = @One(select = "projectType")),
            @Result(property = "projectIntro",column = "id",many = @Many(select = "projectIntro")),
            @Result(property = "projectGalleries",column = "id",many = @Many(select = "projectGalleries")),
            @Result(property = "propertyTypes",column = "id",many = @Many(select = "propertyTypes"))
    })
    ProjectDetail projectDetail(@Param("projectID") int projectID);

    @Select("SELECT name " +
            "FROM country " +
            "WHERE id=#{country_id}")
    String country();

    @Select("SELECT name " +
            "FROM project_type " +
            "WHERE id=#{project_type_id}")
    String projectType();

    @Select("SELECT id,name,description " +
            "FROM project_intro " +
            "WHERE project_id=#{id}")
    List<ProjectIntro> projectIntro();

    @Select("SELECT url " +
            "FROM project_gallery " +
            "WHERE project_id=#{id} AND type='image'")
    @Results({
            @Result(property = "image",column = "url")
    })
    List<ProjectGallery> projectGalleries();

    @Select("SELECT * " +
            "FROM property_type " +
            "WHERE project_id=#{id}")
    @Results({
            @Result(property = "type",column = "name")
    })
    List<PropertyType> propertyTypes();

    @Select("SELECT id " +
            "FROM favorite " +
            "WHERE project_id=#{projectID} AND user_id=#{userID}")
    Integer favorite(@Param("projectID") int projectID, @Param("userID") int userID);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email = #{email}")
    Integer getUserIDByEmail(String email);

}
