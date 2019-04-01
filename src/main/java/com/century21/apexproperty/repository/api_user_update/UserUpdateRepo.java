package com.century21.apexproperty.repository.api_user_update;

import com.century21.apexproperty.repository.api_user_info.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUpdateRepo {
    @Select("UPDATE users " +
            "SET first_name=#{updateInfo.firstName}," +
            "last_name=#{updateInfo.lastName}," +
            "gender=#{updateInfo.gender}," +
            "phone_number=#{updateInfo.phoneNumber} " +
            "WHERE id = #{userID} " +
            "RETURNING id")
    Integer updateUser(@Param("updateInfo") UpdateInfo updateInfo, @Param("userID") int userID);

    @Select("SELECT first_name,last_name,email,gender,phone_number,image " +
            "FROM users " +
            "WHERE id=#{userID}")
    @Results({
            @Result(property = "firstName",column = "first_name"),
            @Result(property = "lastName",column = "last_name"),
            @Result(property = "phoneNumber",column = "phone_number")
    })
    UserInfo findUser(int userID);

    @Select("SELECT name " +
            "FROM country " +
            "WHERE id=#{country_id} " +
            "ORDER BY id")
    String projectCountry();

    @Select("SELECT name " +
            "FROM project_type " +
            "WHERE id=#{project_type_id} " +
            "ORDER BY id")
    String projectType();

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email ILIKE #{email}")
    Integer getUserIDByEmail(String email);
}
