package com.century21.century21cambodia.repository.api_user_reset_pass;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResetPassRepo {

    @Update("UPDATE verification SET enable = true " +
            "WHERE email = #{email} AND code = #{code}")
    Integer findIDByEmailAndCode(@Param("email")String email,@Param("code")int code);

    @Update("UPDATE users SET password = crypt(#{password},gen_salt('bf')) " +
            "WHERE email = #{email}")
    Integer updateUserPassword(@Param("email")String email,@Param("password")String password);

    @Delete("DELECT FROM verification WHERE name = #{email} ")
    Integer deleteVerification(String email);
}
