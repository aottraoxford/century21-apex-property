package com.century21.century21cambodia.repository.user_info;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepo {
    @Select("SELECT first_name,last_name,email,gender,phone_number,image " +
            "FROM users " +
            "WHERE id=#{userID}")
    @Results({
        @Result(property = "firstName",column = "first_name"),
        @Result(property = "lastName",column = "last_name"),
        @Result(property = "phoneNumber",column = "phone_number")
    })
    UserInfo userInfo(@Param("userID")int userID);
}
