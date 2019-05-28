package com.century21.apexproperty.repository.api_get_noti;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GetNofiRepo {
    @Select("SELECT id " +
            "FROM users " +
            "WHERE email ILIKE #{email}")
    Integer getUserID(String email);

    @Select("SELECT banner " +
            "FROM events " +
            "WHERE id=#{eventID}")
    String getBanner(int eventID);

    @Select("SELECT name " +
            "FROM property_files " +
            "WHERE property_id=#{propertyID} " +
            "ORDER BY id limit 1")
    String getPropertyThumbnail(int propertyID);

    @Select("SELECT thumbnail " +
            "FROM project " +
            "WHERE id=#{projectID}")
    String getThumbnail(int projectID);

    @Select("SELECT * " +
            "FROM noti " +
            "WHERE user_id = #{userID}")
    @Results({
            @Result(property = "refID",column = "ref_id"),
            @Result(property = "userID",column = "user_id"),
            @Result(property = "date",column = "created_at")
    })
    List<GetNoti> getNoti(int userID);

    @Select("SELECT count(id) " +
            "FROM noti " +
            "WHERE user_id = #{userID}")
    int getNotiCount(int userID);
}
