package com.century21.apexproperty.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepo {
    @Insert("INSERT INTO user_log(description,user_id) " +
            "VALUES(#{description},#{userID})")
    void insertUserLog(@Param("description")String description,@Param("userID")int userID);
}
