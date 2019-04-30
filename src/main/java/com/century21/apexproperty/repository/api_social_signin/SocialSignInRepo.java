package com.century21.apexproperty.repository.api_social_signin;

import com.century21.apexproperty.model.request.SocialSignIn;
import com.century21.apexproperty.service.authorize.Authority;
import com.century21.apexproperty.service.authorize.SocialAccount;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SocialSignInRepo {
    @Select("SELECT authority.role " +
            "FROM authority " +
            "INNER JOIN authorizations ON authority.id=authorizations.authority_id " +
            "INNER JOIN users on users.id=authorizations.users_id " +
            "WHERE users.appid=#{appID}")
    String findRoleByAppID(String appID);

    @Select("SELECT count(id) FROM users WHERE email = #{email}")
    int isEmailExist(String email);

    @Select("SELECT email,phone_number,password,id " +
            "FROM users " +
            "WHERE appid=#{email} AND enable IS true")
    @Results({
            @Result(property = "phoneNumber",column = "phone_number"),
            @Result(property = "socialId",column = "password"),
            @Result(property = "authorities",column = "id",many = @Many(select="authorities"))
    })
    SocialAccount socialAccount(@Param("email") String email);
    @Select("SELECT authority.role " +
            "FROM authority " +
            "INNER JOIN authorizations ON authority.id=authorizations.authority_id " +
            "WHERE authorizations.users_id=#{id}")
    List<Authority> authorities();

    @Select("SELECT COUNT(id) " +
            "FROM users " +
            "WHERE appid=#{socialId}")
    int checkSocialAccount(@Param("socialId") String socialId);

    @Insert("INSERT INTO users(image,email,password,first_name,last_name,phone_number,gender,account_type,appid,enable) " +
            "VALUES (#{socialSignIn.photo},#{socialSignIn.email},crypt(#{socialSignIn.socialId},gen_salt('bf')),#{socialSignIn.firstName},#{socialSignIn.lastName},#{socialSignIn.phoneNumber},#{socialSignIn.gender},#{socialSignIn.socialType},#{socialSignIn.socialId},true)")
    Integer saveSocialSignIn(@Param("socialSignIn") SocialSignIn socialSignIn);

    @Update("UPDATE users " +
            "SET " +
            "email=#{socialSignIn.email}," +
            "first_name=#{socialSignIn.firstName}" +
            ",last_name=#{socialSignIn.lastName}," +
            "phone_number=#{socialSignIn.phoneNumber}," +
            "gender=#{socialSignIn.gender}," +
            "image=#{socialSignIn.photo} " +
            "WHERE appid=#{socialSignIn.socialId}")
    Integer updateSocialSignIn(@Param("socialSignIn") SocialSignIn socialSignIn);
}
