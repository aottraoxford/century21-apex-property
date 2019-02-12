package com.century21.century21cambodia.service.api_user_reset_pass;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_user_reset_pass.UserResetPassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserResetPassServiceImpl implements UserResetPassService {
    @Autowired
    private UserResetPassRepo userResetPassRepo;
    @Override
    public boolean updateUserPassword(String base64) {
        try{
            byte[] decodedBytes = Base64.getDecoder().decode(base64);
            String decodedString = new String(decodedBytes);
            if(decodedString.contains("|")){
                String s[]=decodedString.split("\\|");
                if(s.length==3){
                    String email = s[0],pass=s[1],code=s[2];
                    Integer check=userResetPassRepo.enableByEmailAndCode(email,Integer.parseInt(code));
                    if(check!=null){
                        userResetPassRepo.updateUserPassword(email,pass);
                        userResetPassRepo.deleteVerification(email);
                        return true;
                    }
                }
            }
        }catch (IllegalArgumentException ex){
            return false;
        }
        return false;
    }
}
