package com.century21.century21cambodia.service.api_social_signin;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.model.request.SocialSignIn;
import com.century21.century21cambodia.model.response.CustomResponse;
import com.century21.century21cambodia.model.response.OAuth2;
import com.century21.century21cambodia.repository.api_social_signin.SocialSignInRepo;
import com.century21.century21cambodia.util.JwtUtil;
import com.century21.century21cambodia.util.Url;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SocialSignInServiceImpl implements SocialSignInService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SocialSignInRepo socialSignInRepo;

    @Override
    public ResponseEntity socialSignIn(String token) {
        SocialSignIn socialSignIn = (SocialSignIn) jwtUtil.tokenToObject(token, "123", SocialSignIn.class);
        CustomResponse customResponse;


        if (socialSignIn.getEmail()==null || socialSignIn.getEmail().equalsIgnoreCase("null")) {
            socialSignIn.setEmail(socialSignIn.getSocialId());
        }else socialSignIn.setEmail(socialSignIn.getEmail()+"|"+UUID.randomUUID());
        if (socialSignInRepo.checkSocialAccount(socialSignIn.getSocialId()) < 1) {
            if (socialSignInRepo.saveSocialSignIn(socialSignIn) < 1) {
                throw new CustomRuntimeException(500, "Insert Data fail.");
            }
        }
        try {
            HttpResponse<OAuth2> jsonResponse = Unirest.post(Url.oauthTokenUrl)
                    .header("accept", "application/json")
                    .header("Authorization", "Basic YzIxYzoxMjM=")
                    .queryString("grant_type", "password")
                    .queryString("username", socialSignIn.getSocialId())
                    .queryString("password", socialSignIn.getSocialId())
                    .asObject(OAuth2.class);
            customResponse = new CustomResponse(200, jsonResponse.getBody());
        } catch (UnirestException e) {
            customResponse = new CustomResponse(500);
            customResponse.setStatus(e.getMessage());
            return customResponse.httpResponse();
        }

        return customResponse.httpResponse("result");
    }
}
