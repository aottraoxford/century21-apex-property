package com.century21.century21cambodia.repository.api_log;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepo {
    @Insert("INSERT INTO log(message,route) " +
            "VALUES (#{message},#{route})")
    Integer postLog(@Param("message")String message,@Param("route")String route);

    @Select("SELECT * " +
            "FROM log " +
            "WHERE route ILIKE #{route}")
    List<Log> getLog(String route);
}
