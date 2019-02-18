package com.century21.repository.api_user_upload_image;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserUploadImageRepo {

    @Select("SELECT image " +
            "FROM users " +
            "WHERE id=#{userID}")
    String findImageName(int userID);

    @Update("UPDATE users SET image = #{filename} " +
            "WHERE id=#{userID}")
    Integer saveUserImage(int userID, String filename);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email ILIKE #{email} ")
    Integer getIDByEmail(String email);

    @Select("SELECT count(id) " +
            "FROM users " +
            "WHERE id=#{id}")
    int checkUserID(int id);

    @Select("SELECT account_type " +
            "FROM users " +
            "WHERE id=#{userID}")
    String getAccountType(int userID);
}
