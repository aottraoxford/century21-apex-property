package com.century21.century21cambodia.repository.api_save_noti;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveNotiRepo {
    @Insert("INSERT INTO noti(title,message,image,user_id) " +
            "VALUES (#{n.title},#{n.message},#{n.image},#{userID})")
    Integer SaveNoti(@Param("n")SaveNoti saveNoti,@Param("userID")int userID);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email like #{email}")
    Integer getUserID(@Param("email")String email);
}
