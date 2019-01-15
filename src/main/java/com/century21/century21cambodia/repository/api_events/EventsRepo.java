package com.century21.century21cambodia.repository.api_events;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepo {
    @Select("SELECT title,description,banner " +
            "FROM events " +
            "WHERE enable IS true")
    List<Events> events();
}
