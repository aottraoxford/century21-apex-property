package com.century21.century21cambodia.repository.api_post_event;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface PostEventRepo {
    @Select("INSERT INTO events (title,description,banner,event_date) " +
            "VALUES (#{title},#{desc},#{banner},#{eventDate}) " +
            "RETURNING id")
    Integer postEvent(@Param("title")String title, @Param("desc")String description, @Param("eventDate")Timestamp eventDate ,@Param("banner")String banner);
}
