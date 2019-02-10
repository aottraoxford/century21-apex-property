package com.century21.century21cambodia.repository.api_social_signin;

import com.century21.century21cambodia.model.request.SocialSignIn;
import com.century21.century21cambodia.service.authorize.Authority;
import com.century21.century21cambodia.service.authorize.SocialAccount;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialSignInRepo {
    @Select("SELECT email,phone_number,password,id " +
            "FROM users " +
            "WHERE appid=#{email} AND enable IS true")
    @Results({
            @Result(property = "phoneNumber",column = "phone_number"),
            @Result(property = "socialId",column = "password"),
            @Result(property = "authorities",column = "id",many = @Many(select="authorities"))
    })
    SocialAccount socialAccount(@Param("email")String email);
    @Select("SELECT authority.role " +
            "FROM authority " +
            "INNER JOIN authorizations ON authority.id=authorizations.authority_id " +
            "WHERE authorizations.users_id=#{id}")
    List<Authority> authorities();

    @Select("SELECT COUNT(id) " +
            "FROM users " +
            "WHERE appid=#{socialId}")
    int checkSocialAccount(@Param("socialId")String socialId);

    @Insert("INSERT INTO users(image,email,password,first_name,last_name,phone_number,gender,account_type,appid,enable) " +
            "VALUES (#{socialSignIn.photo},#{socialSignIn.email},crypt(#{socialSignIn.socialId},gen_salt('bf')),#{socialSignIn.firstName},#{socialSignIn.lastName},#{socialSignIn.phoneNumber},#{socialSignIn.gender},#{socialSignIn.socialType},#{socialSignIn.socialId},true)")
    int saveSocialSignIn(@Param("socialSignIn")SocialSignIn socialSignIn);
}
