package com.century21.repository;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo {

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email =#{email}")
    Integer findUserIDByEmail(String email);
}
