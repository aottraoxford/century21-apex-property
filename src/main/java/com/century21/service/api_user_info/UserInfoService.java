package com.century21.service.api_user_info;

import com.century21.repository.api_user_info.UserInfo;

public interface UserInfoService {
    UserInfo userInfo(Integer userID, String email);
}
