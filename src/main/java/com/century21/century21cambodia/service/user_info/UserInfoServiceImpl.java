package com.century21.century21cambodia.service.user_info;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.user_info.UserInfo;
import com.century21.century21cambodia.repository.user_info.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepo userInfoRepo;
    @Override
    public UserInfo userInfo(int userID) {
        UserInfo userInfo=userInfoRepo.userInfo(userID);
        if(userInfo==null)
            throw new CustomRuntimeException(404,"ZERO RESULT");
        return userInfo;
    }
}
