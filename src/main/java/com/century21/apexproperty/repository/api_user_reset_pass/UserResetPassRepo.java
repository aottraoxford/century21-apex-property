package com.century21.apexproperty.repository.api_user_reset_pass;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResetPassRepo {


    @Update("UPDATE users SET password = crypt(#{password},gen_salt('bf')) " +
            "WHERE email = #{email}")
    Integer updateUserPassword(@Param("email") String email, @Param("password") String password);

    @Delete("DELETE FROM verification WHERE name = #{email} AND code IS NULL")
    Integer deleteVerification(String email);

    @Select("SELECT name,password " +
            "FROM verification " +
            "WHERE uuid=#{uuid} AND code IS NULL")
    @Results({
            @Result(property = "pass", column = "password"),
            @Result(property = "email", column = "name")
    })
    Auth userPass(@Param("uuid") String uuid);

}
