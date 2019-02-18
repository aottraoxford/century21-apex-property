package com.century21.repository.api_allcity;

import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo {

    @Select("SELECT DISTINCT(country.id),country.name " +
            "FROM country " +
            "INNER JOIN project ON country.id=project.country_id " +
            "WHERE project.isdisplay IS TRUE ")
    @Results({
            @Result(property = "countryId",column ="id"),
            @Result(property = "countryName",column = "name"),
            @Result(property = "cities",column = "id",many=@Many(select = "cities"))
    })
    List<Country> countries();

    @Select("SELECT DISTINCT(city) " +
            "FROM project " +
            "WHERE country_id = #{id} AND isdisplay IS true AND city IS NOT NULL")
    List<String> cities(int cid);
}
