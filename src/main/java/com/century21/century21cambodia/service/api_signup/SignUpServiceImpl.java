package com.century21.century21cambodia.service.api_signup;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.model.request.SignUp;
import com.century21.century21cambodia.repository.api_signup.SignUpRepo;
import com.century21.century21cambodia.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;

@org.springframework.stereotype.Service(value = "signUpService")
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private MailService mailService;
    @Autowired
    private SignUpRepo signupRepo;

    @Autowired
    public SignUpServiceImpl(SignUpRepo signUpRepo){
        this.signupRepo=signUpRepo;
    }

    @Override
    public void signUp(SignUp signUp) {
        if(signupRepo.countEmailByEmail(signUp.getEmail())>0){
            throw new CustomRuntimeException(409,"Email already exists");
        }
        if(signupRepo.isEmailVerify(signUp.getEmail())<1){
            throw new CustomRuntimeException(401,"Your email not yet verify");
        }
        if(signupRepo.signUp(signUp)<1){
            throw new CustomRuntimeException(500,"ERROR CODE");
        }
    }

}
