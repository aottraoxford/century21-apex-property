package com.century21.repository.api_user_question;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuestionRepo {
    @Insert("INSERT INTO contact(name,phone,country,issue) " +
            "VALUES(#{userQuestion.name},#{userQuestion.phone},#{userQuestion.country},#{userQuestion.issue}) ")
    int saveUserQuestion(@Param("userQuestion") UserQuestion userQuestion);
}
