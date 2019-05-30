package com.century21.apexproperty.repository.api_user_contact;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepo {
    @Select("INSERT into contact(name,phone,email,project_id,property_id) " +
            "VALUES (#{userContact.name},#{userContact.phone},#{userContact.email},#{userContact.projectID},#{userContact.propertyID}) returning id")
    int saveUserContact(@Param("userContact") UserContact userContact);
}
