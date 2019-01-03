package com.century21.century21cambodia.repository.api_user_upload_image;

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
    int saveUserImage(int userID,String filename);

}
