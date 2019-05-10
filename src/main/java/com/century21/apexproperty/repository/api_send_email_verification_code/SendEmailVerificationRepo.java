package com.century21.apexproperty.repository.api_send_email_verification_code;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface SendEmailVerificationRepo {
    @Insert("INSERT INTO verification (name,code) " +
            "VALUES (#{email},#{code})")
    int saveEmailId(@Param("email") String email, @Param("code") int code);

    @Select("SELECT COUNT(id) " +
            "FROM users " +
            "WHERE email = #{email} AND account_type = 'origin'")
    int countEmailByEmail(@Param("email") String email);

    @Select("SELECT expired " +
            "FROM verification " +
            "WHERE name = #{email} and enable is false " +
            "ORDER BY id DESC limit 1")
    Date findEmailExpiredDate(String email);
}
