package com.century21.apexproperty.service.api_user_question;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.UserRepo;
import com.century21.apexproperty.repository.api_user_question.UserQuestion;
import com.century21.apexproperty.repository.api_user_question.UserQuestionRepo;
import com.century21.apexproperty.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserQuestionServiceImpl implements UserQuestionService {
    @Autowired
    private UserQuestionRepo userQuestionRepo;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepo userRepo;
    @Override
    public void saveUserQuestion(UserQuestion userQuestion) {
        int i = userQuestionRepo.saveUserQuestion(userQuestion);
        if(i<1){
            throw new CustomRuntimeException(500,"Can not save.");
        }
        UserRepo.MailAccount mailAccount=userRepo.findOneMailAccount();
        String template =   "<p>Name : "+userQuestion.getName()+"</p>" +
                            "<p>From : "+userQuestion.getCountry()+"</p>" +
                            "<p>Phone : "+userQuestion.getPhone()+"</p>" +
                            "<p>Issue : "+userQuestion.getIssue()+"</p>";
        mailService.sendMail("INQUIRY from client",mailAccount.getEmail(),template);
    }
}
