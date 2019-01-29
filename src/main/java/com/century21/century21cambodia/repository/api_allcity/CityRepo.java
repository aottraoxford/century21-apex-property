package com.century21.century21cambodia.repository.api_allcity;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepo {
    @Select("SELECT DISTINCT(city) " +
            "FROM project " +
            "WHERE country_id = #{cid} AND isdisplay IS true AND city IS NOT NULL")
    List<String> cities(int cid);
}
