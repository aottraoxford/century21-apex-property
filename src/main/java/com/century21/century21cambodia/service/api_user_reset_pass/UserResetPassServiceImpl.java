package com.century21.century21cambodia.service.api_user_reset_pass;

import com.century21.century21cambodia.repository.api_user_reset_pass.UserResetPassRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserResetPassServiceImpl implements UserResetPassService {
    @Autowired
    private UserResetPassRepo userResetPassRepo;
    @Override
    public void updateUserPassword(String email, int code) {

    }
}
