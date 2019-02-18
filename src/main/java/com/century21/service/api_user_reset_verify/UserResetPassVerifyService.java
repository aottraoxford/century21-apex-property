package com.century21.service.api_user_reset_verify;

public interface UserResetPassVerifyService {
    void sendLinkToEmail(String email, String pass);
}
