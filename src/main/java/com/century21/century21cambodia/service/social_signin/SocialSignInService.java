package com.century21.century21cambodia.service.social_signin;

import org.springframework.http.ResponseEntity;

public interface SocialSignInService {
    ResponseEntity socialSignIn(String token);
}
