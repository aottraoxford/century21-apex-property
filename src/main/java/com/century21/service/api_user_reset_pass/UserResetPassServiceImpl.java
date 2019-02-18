package com.century21.service.api_user_reset_pass;

import com.century21.repository.api_user_reset_pass.Auth;
import com.century21.repository.api_user_reset_pass.UserResetPassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserResetPassServiceImpl implements UserResetPassService {
    @Autowired
    private UserResetPassRepo userResetPassRepo;
    @Override
    public boolean updateUserPassword(String base64) {
        Auth auth=userResetPassRepo.userPass(base64);
        if(auth==null) return false;
        else {
            userResetPassRepo.updateUserPassword(auth.getEmail(),auth.getPass());
            userResetPassRepo.deleteVerification(auth.getPass());
            return true;
        }
    }
}
