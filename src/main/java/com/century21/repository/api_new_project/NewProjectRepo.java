package com.century21.repository.api_new_project;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewProjectRepo {
    @Select("INSERT INTO project(city,name,grr,country_id,project_type_id,completed_date,built_date,description,start_price,end_price,avg_rent_from,avg_rent_to,down_payment,rent_or_buy,address_1,address_2) " +
            "VALUES (#{project.city},#{project.name},#{project.grr},#{project.countryID},#{project.projectTypeID},#{project.completedDate},#{project.builtDate},#{project.description},#{project.minPrice},#{project.maxPrice},#{project.avgRentFrom},#{project.avgRentTo},#{project.downPayment},#{project.status},#{project.addressOne},#{project.addressTwo}) " +
            "RETURNING id")
    int saveProject(@Param("project") Project project);

    @Insert("INSERT INTO project_intro(name,description,project_id) " +
            "VALUES(#{intro.name},#{intro.description},#{pid}) ")
    int saveProjectIntroduction(@Param("intro") ProjectIntroduction projectIntroduction, @Param("pid") int projectID);

    @Insert("INSERT INTO property_type(name,bedroom,floor,width,height,bathroom,parking,project_id) " +
            "VALUES (#{pt.type},#{pt.bedroom},#{pt.floor},#{pt.width},#{pt.height},#{pt.bathroom},#{pt.parking},#{pid})")
    int savePropertyType(@Param("pt") PropertyType propertyType, @Param("pid") int projectID);

    @Select("SELECT country.id,country.name FROM country " +
            "INNER JOIN project ON country.id = project.country_id " +
            "WHERE country.name ILIKE #{name} AND project.isdisplay IS TRUE")
    List<Country> countries(@Param("name") String name);

    @Insert("INSERT INTO tower_type(project_id) " +
            "VALUES(#{projectID})")
    Integer saveTowerType(int projectID);
}
