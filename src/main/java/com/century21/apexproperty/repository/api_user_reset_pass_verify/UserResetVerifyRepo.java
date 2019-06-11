package com.century21.apexproperty.repository.api_user_reset_pass_verify;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResetVerifyRepo {
    @Select("INSERT INTO verification(password,name) " +
            "VALUES (#{password},#{email}) " +
            "RETURNING uuid")
    String storeCode(@Param("password") String password, @Param("email") String email);

    @Select("SELECT count(id) " +
            "FROM users " +
            "WHERE email = #{email}")
    int checkEmail(String email);
}
