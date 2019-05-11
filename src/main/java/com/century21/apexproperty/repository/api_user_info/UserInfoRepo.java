package com.century21.apexproperty.repository.api_user_info;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepo {
    @Select("SELECT users.id,authority.role,first_name,last_name,email,gender,phone_number,image,account_type,parent_id " +
            "FROM users " +
            "INNER JOIN authorizations ON authorizations.users_id=#{userID} " +
            "INNER JOIN authority ON authorizations.authority_id=authority.id " +
            "LEFT JOIN project ON project.user_id=users.id " +
            "WHERE users.id=#{userID}")
    @Results({
        @Result(property = "id",column = "id"),
        @Result(property = "firstName",column = "first_name"),
        @Result(property = "lastName",column = "last_name"),
        @Result(property = "phoneNumber",column = "phone_number"),
        @Result(property = "accountType",column = "account_type"),
        @Result(property = "groupID",column = "parent_id")
    })
    UserInfo userInfo(@Param("userID") int userID);

    @Select("SELECT id " +
            "FROM users " +
            "WHERE email ILIKE #{email} ")
    Integer getIDByEmail(String email);

}
