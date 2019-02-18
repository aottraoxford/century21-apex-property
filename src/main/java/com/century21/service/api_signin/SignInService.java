package com.century21.service.api_signin;

import java.util.List;

public interface SignInService {
    List<String> emailExist(String email);
}
