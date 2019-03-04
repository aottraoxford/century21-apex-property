package com.century21.repository.api_send_email_verification_code;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SendEmailVerificationRepo {
    @Insert("INSERT INTO verification (name,code) " +
            "VALUES (#{email},#{code})")
    int saveEmailId(@Param("email") String email, @Param("code") int code);

    @Select("SELECT COUNT(id) " +
            "FROM users " +
            "WHERE email = #{email} AND account_type = 'origin'")
    int countEmailByEmail(@Param("email") String email);

    @Delete("DELETE " +
            "FROM verification " +
            "WHERE name = #{email} AND code = #{code} AND enable IS FALSE")
    int removeVerifyEmail(@Param("email") String email, @Param("code") int code);
}
