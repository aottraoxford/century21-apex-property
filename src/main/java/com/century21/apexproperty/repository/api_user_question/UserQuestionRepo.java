package com.century21.apexproperty.repository.api_user_question;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestionRepo {
    @Insert("INSERT INTO contact(name,phone,email,country,issue,type) " +
            "VALUES(#{userQuestion.name},#{userQuestion.phone},#{userQuestion.email},#{userQuestion.country},#{userQuestion.issue},'inquiry') ")
    int saveUserQuestion(@Param("userQuestion") UserQuestion userQuestion);
}
