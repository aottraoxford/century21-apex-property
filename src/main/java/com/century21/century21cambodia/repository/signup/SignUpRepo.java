package com.century21.century21cambodia.repository.signup;

import com.century21.century21cambodia.model.request.SignUp;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpRepo {

    @Select("SELECT COUNT(id) " +
            "FROM users " +
            "WHERE email = #{email} AND account_type = 'origin'")
    int countEmailByEmail(@Param("email")String email);

    @Select("SELECT COUNT(id) " +
            "FROM verification " +
            "WHERE name = #{email} AND enable IS true " +
            "LIMIT 1")
    int isEmailVerify(@Param("email")String email);

    @Insert("INSERT INTO verification (name,code) " +
            "VALUES (#{email},#{code})")
    int saveEmailId(@Param("email") String email,@Param("code") int code);

    @Insert("INSERT INTO users (first_name,last_name,gender,email,phone_number,password) " +
            "VALUES (#{signUp.firstName},#{signUp.lastName},#{signUp.gender},#{signUp.email},#{signUp.phoneNumber},crypt(#{signUp.password},gen_salt('bf')))")
    int signUp(@Param("signUp")SignUp signUp);

    @Update("UPDATE verification " +
            "SET enable = true " +
            "WHERE name=#{email} AND code=#{code}")
    int enableEmail(@Param("email") String email,@Param("code")int code);

    @Delete("DELETE " +
            "FROM verification " +
            "WHERE name = #{email} AND code = #{code}")
    int removeVerifyEmail(@Param("email")String email,@Param("code")int code);

}
