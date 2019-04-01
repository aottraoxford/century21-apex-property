package com.century21.apexproperty.service.api_user_update;

import com.century21.apexproperty.repository.api_user_info.UserInfo;
import com.century21.apexproperty.repository.api_user_update.UpdateInfo;

import java.security.Principal;

public interface UserUpdateService {
    UserInfo userUpdate(UpdateInfo updateInfo, Principal principal);
}
