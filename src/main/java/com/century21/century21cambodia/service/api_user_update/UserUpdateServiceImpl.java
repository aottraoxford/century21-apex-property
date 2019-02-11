package com.century21.century21cambodia.service.api_user_update;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_user_info.UserInfo;
import com.century21.century21cambodia.repository.api_user_update.UpdateInfo;
import com.century21.century21cambodia.repository.api_user_update.UserUpdateRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserUpdateServiceImpl implements UserUpdateService {
    @Autowired
    private UserUpdateRepo userUpdateRepo;
    @Override
    public UserInfo userUpdate(UpdateInfo updateInfo, Principal principal) {
        if(principal==null) throw new CustomRuntimeException(400,"Token required");
        Integer userID=userUpdateRepo.getUserIDByEmail(principal.getName());
        if(userID==null) throw new CustomRuntimeException(404,"USER NOT FOUND");
        if(userUpdateRepo.updateUser(updateInfo,userID)==null) throw new CustomRuntimeException(400,"CANT UPDATE USER");
        return userUpdateRepo.findUser(userID);
    }
}
