package com.century21.apexproperty.exception;

import com.century21.apexproperty.model.response.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {

    @Override
    public ResponseEntity translate(Exception ex) throws Exception {
        CustomResponse customResponse = new CustomResponse(401);
        customResponse.setStatus(ex.getMessage());
        return customResponse.httpResponse();
    }
}
