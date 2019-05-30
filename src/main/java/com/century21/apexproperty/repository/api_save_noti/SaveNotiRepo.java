package com.century21.apexproperty.repository.api_save_noti;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveNotiRepo {
    @Insert("INSERT INTO noti(title,message,user_id,type,ref_id) " +
            "VALUES (#{n.title},#{n.message},#{userID},#{n.type},#{n.refID})")
    Integer SaveNoti(@Param("n") SaveNoti saveNoti, @Param("userID") Integer userID);

}
