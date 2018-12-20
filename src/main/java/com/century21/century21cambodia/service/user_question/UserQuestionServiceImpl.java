package com.century21.century21cambodia.service.user_question;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.user_question.UserQuestion;
import com.century21.century21cambodia.repository.user_question.UserQuestionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQuestionServiceImpl implements UserQuestionService {
    @Autowired
    private UserQuestionRepo userQuestionRepo;
    @Override
    public void saveUserQuestion(UserQuestion userQuestion) {
        int i = userQuestionRepo.saveUserQuestion(userQuestion);
        if(i<1){
            throw new CustomRuntimeException(500,"Cant not save.");
        }
    }
}
