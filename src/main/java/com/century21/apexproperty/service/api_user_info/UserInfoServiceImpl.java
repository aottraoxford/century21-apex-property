package com.century21.apexproperty.service.api_user_info;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_user_info.UserInfo;
import com.century21.apexproperty.repository.api_user_info.UserInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserInfoRepo userInfoRepo;

    @Override
    public UserInfo userInfo(Integer userID, String email) {
        if (userID == null) {
            userID = userInfoRepo.getIDByEmail(email);
            if (userID == null) throw new CustomRuntimeException(404, "USER NOT EXIST");
        }

        System.out.println(userID);

        UserInfo userInfo = userInfoRepo.userInfo(userID);


        if (userInfo == null)
            throw new CustomRuntimeException(404, "ZERO RESULT");
        return userInfo;
    }
}
