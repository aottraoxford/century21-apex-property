package com.century21.repository.api_post_event;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface PostEventRepo {
    @Select("INSERT INTO events (title,description,banner,event_date,enable,type) " +
            "VALUES (#{title},#{desc},#{banner},#{eventDate},false,'event') " +
            "RETURNING id")
    Integer postEvent(@Param("title") String title, @Param("desc") String description, @Param("eventDate") Timestamp eventDate, @Param("banner") String banner);
}
