package com.century21.century21cambodia.repository.api_user_forget_pass;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserForgetPassRepo {
    @Insert("INSERT INTO verification(name,code) " +
            "VALUES (#{email},#{code}")
    Integer storeCode(@Param("email")String email,@Param("code")String code);

    @Select("SELECT id " +
            "FROM verification " +
            "WHERE email ilike #{email} code ilike #{code}")
    Integer findIDByEmailAndCode(@Param("email")String email,@Param("code")String code);


}
