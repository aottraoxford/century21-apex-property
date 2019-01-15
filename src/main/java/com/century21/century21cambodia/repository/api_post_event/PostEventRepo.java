package com.century21.century21cambodia.repository.api_post_event;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEventRepo {
    @Insert("INSERT INTO events (title,description,banner) " +
            "VALUES (#{title},#{desc},#{banner})")
    int postEvent(@Param("title")String title,@Param("desc")String description,@Param("banner")String banner);
}
