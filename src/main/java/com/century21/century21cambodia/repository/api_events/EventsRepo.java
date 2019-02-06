package com.century21.century21cambodia.repository.api_events;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepo {
    @Select("SELECT id,title,description,banner,event_date " +
            "FROM events " +
            "WHERE enable IS true AND type ILIKE 'event' " +
            "ORDER BY id DESC")
    @Results({
            @Result(property = "date",column = "event_date")
    })
    List<Events> events();

    @Update("UPDATE events " +
            "SET enable = false " +
            "WHERE id = #{eventID}")
    Integer updateEvent(int eventID);
}
