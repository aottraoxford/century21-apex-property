package com.century21.apexproperty.repository.api_silder_add;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AddSliderRepo {
    @Select("INSERT INTO events(title,banner,type,enable) " +
            "VALUES (#{name},#{image},'slider','false') " +
            "RETURNING id")
    Integer addSlider(@Param("name") String name, @Param("image") String image);
}
