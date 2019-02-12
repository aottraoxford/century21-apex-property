package com.century21.century21cambodia.repository.api_user_reset_pass_verify;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResetVerifyRepo {
    @Insert("INSERT INTO verification(name,code) " +
            "VALUES (#{email},#{code}")
    Integer storeCode(@Param("email")String email, @Param("code")int code);
}
