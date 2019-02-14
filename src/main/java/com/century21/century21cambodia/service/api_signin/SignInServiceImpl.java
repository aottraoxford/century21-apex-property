package com.century21.century21cambodia.service.api_signin;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_signin.SignInRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignInServiceImpl implements SignInService {
    @Autowired
    private SignInRepo signInRepo;
    @Override
    public List<String> emailExist(String email) {
        Boolean emailExist=signInRepo.emailExist(email);
        if(emailExist==null){
            throw new CustomRuntimeException(404,"Email not exist");
        }
        if(emailExist==false){
            throw new CustomRuntimeException(401,"Email not yet enable");
        }
        return signInRepo.roles(signInRepo.findIdByEmail(email));
    }
}
