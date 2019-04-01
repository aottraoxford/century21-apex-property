package com.century21.apexproperty.service.api_signin;

import java.util.List;

public interface SignInService {
    List<String> emailExist(String email);
}
