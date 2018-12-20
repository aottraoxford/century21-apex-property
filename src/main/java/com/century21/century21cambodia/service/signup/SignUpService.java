package com.century21.century21cambodia.service.signup;

import com.century21.century21cambodia.model.request.SignUp;
import org.apache.ibatis.annotations.Param;

public interface SignUpService {

    void saveEmailId(String email);
    void enableEmail(String email, int code);
    void signUp(SignUp signUp);
    void removeVerifyEmail(String email,int code);

}
