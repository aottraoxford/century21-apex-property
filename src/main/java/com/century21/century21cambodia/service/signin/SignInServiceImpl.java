package com.century21.century21cambodia.service.signin;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.model.response.CustomResponse;
import com.century21.century21cambodia.repository.signin.SignInRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    private SignInRepo signInRepo;
    @Override
    public void emailExist(String email) {
        Boolean emailExist=signInRepo.emailExist(email);
        if(emailExist==null){
            throw new CustomRuntimeException(404,"Email not exist");
        }
        if(emailExist==false){
            throw new CustomRuntimeException(401,"Email not yet enable");
        }
    }
}
