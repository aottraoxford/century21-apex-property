package com.century21.apexproperty.service.api_social_signin;

import org.springframework.http.ResponseEntity;

public interface SocialSignInService {
    ResponseEntity socialSignIn(String token);
}
