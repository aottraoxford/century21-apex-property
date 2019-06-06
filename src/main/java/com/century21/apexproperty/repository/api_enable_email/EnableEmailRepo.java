package com.century21.apexproperty.repository.api_enable_email;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface EnableEmailRepo {
    @Update("UPDATE verification " +
            "SET enable = true " +
            "WHERE name ilike #{email} AND code=#{code}")
    int enableEmail(@Param("email") String email, @Param("code") int code);

    @Delete("DELECT FROM vrification WHERE email ilike #{email}")
    int removeVerifiesByEmail(String email);
}
