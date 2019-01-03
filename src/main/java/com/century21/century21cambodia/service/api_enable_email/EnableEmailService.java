package com.century21.century21cambodia.service.api_enable_email;

import org.apache.ibatis.annotations.Param;

public interface EnableEmailService {
    void enableEmail(String email, int code);
}
