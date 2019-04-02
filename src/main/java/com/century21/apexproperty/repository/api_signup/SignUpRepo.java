package com.century21.apexproperty.repository.api_signup;

import com.century21.apexproperty.model.request.SignUp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SignUpRepo {

    @Select("SELECT COUNT(id) " +
            "FROM users " +
            "WHERE email ilike #{email} AND account_type = 'origin'")
    int countEmailByEmail(@Param("email") String email);

    @Select("SELECT COUNT(id) " +
            "FROM verification " +
            "WHERE name = #{email} AND enable IS true " +
            "LIMIT 1")
    int isEmailVerify(@Param("email") String email);

    @Insert("INSERT INTO users (enable,first_name,last_name,gender,email,phone_number,password) " +
            "VALUES (true,#{signUp.firstName},#{signUp.lastName},#{signUp.gender},#{signUp.email},#{signUp.phoneNumber},crypt(#{signUp.password},gen_salt('bf')))")
    int signUp(@Param("signUp") SignUp signUp);

}
