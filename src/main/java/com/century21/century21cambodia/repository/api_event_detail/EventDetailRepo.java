package com.century21.century21cambodia.repository.api_event_detail;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDetailRepo {
    @Select("SELECT id,banner,title,description " +
            "FROM events " +
            "WHERE type ILIKE 'event' AND id = #{id}")
    EventDetail getEventDetail(int id);
}
