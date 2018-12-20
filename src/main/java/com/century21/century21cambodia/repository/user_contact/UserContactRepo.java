package com.century21.century21cambodia.repository.user_contact;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepo {
    @Insert("INSERT into contact(name,phone,email) " +
            "VALUES (#{userContact.name},#{userContact.phone},#{userContact.email})")
    int saveUserContact(@Param("userContact")UserContact userContact);
}
