package com.century21.century21cambodia.service.api_user_reset_verify;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_user_reset_pass.UserResetPassRepo;
import com.century21.century21cambodia.repository.api_user_reset_pass_verify.UserResetVerifyRepo;
import com.century21.century21cambodia.util.MailService;
import com.century21.century21cambodia.util.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserResetPassVerifyServiceImpl implements UserResetPassVerifyService {
    @Autowired
    private MailService mailService;
    @Autowired
    private UserResetVerifyRepo userResetVerifyRepo;
    @Override
    public void sendLinkToEmail(String email,String pass) {
        if(userResetVerifyRepo.checkEmail(email)<1) throw new CustomRuntimeException(400,"Your email not yet register");
        int code = (int)(Math.random()*8999)+1000;
        String codeString = email+"|"+pass+"|"+code;
        String encodeString = Base64.getEncoder().encodeToString(codeString.getBytes());
        userResetVerifyRepo.storeCode(email,code);
        mailService.sendMail(email, Url.host+"api/user/reset/pass/"+encodeString);
    }
}
