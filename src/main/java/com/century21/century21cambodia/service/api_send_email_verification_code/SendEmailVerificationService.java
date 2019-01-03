package com.century21.century21cambodia.service.api_send_email_verification_code;

import org.apache.ibatis.annotations.Param;

public interface SendEmailVerificationService {
    void saveEmailId(String email);
    void removeVerifyEmail(String email, int code);
}
