package com.century21.apexproperty.service.api_user_info;

import com.century21.apexproperty.repository.api_user_info.UserInfo;

public interface UserInfoService {
    UserInfo userInfo(Integer userID, String email);
}
