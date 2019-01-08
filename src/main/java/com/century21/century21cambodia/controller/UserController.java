package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.configuration.upload.FileUploadProperty;
import com.century21.century21cambodia.configuration.upload.FileUploadService;
import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.model.request.EnableEmail;
import com.century21.century21cambodia.model.request.RefreshToken;
import com.century21.century21cambodia.model.request.SignIn;
import com.century21.century21cambodia.model.request.SignUp;
import com.century21.century21cambodia.model.response.CustomResponse;
import com.century21.century21cambodia.model.response.OAuth2;
import com.century21.century21cambodia.repository.api_user_contact.UserContact;
import com.century21.century21cambodia.repository.api_user_question.UserQuestion;
import com.century21.century21cambodia.service.api_enable_email.EnableEmailService;
import com.century21.century21cambodia.service.api_send_email_verification_code.SendEmailVerificationService;
import com.century21.century21cambodia.service.api_user_upload_image.UserUploadImageService;
import com.century21.century21cambodia.service.api_signin.SignInService;
import com.century21.century21cambodia.service.api_signup.SignUpService;
import com.century21.century21cambodia.service.api_social_signin.SocialSignInService;
import com.century21.century21cambodia.service.api_user_contact.UserContactService;
import com.century21.century21cambodia.service.api_user_info.UserInfoService;
import com.century21.century21cambodia.service.api_user_question.UserQuestionService;
import com.century21.century21cambodia.util.Url;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.IOException;

@Api(value = "user management",description = "user management")
@RestController
@Validated //validate request param
public class UserController {

    @Autowired
    private SignUpService signUpService;
    @Autowired
    private SignInService signInService;
    @Autowired
    private SocialSignInService socialSignInService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserQuestionService userQuestionService;
    @Autowired
    private UserContactService userContactService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;

    @Autowired
    private SendEmailVerificationService sendEmailVerificationService;

    @ApiOperation("send 4 number to email to verify")
    @GetMapping(value = "/api/send-email-verification-code",produces = "application/json")
    public ResponseEntity emailVerification(@Email @RequestParam("email") String email, HttpServletRequest httpServletRequest){
        sendEmailVerificationService.saveEmailId(email);
        CustomResponse customResponse=new CustomResponse(200);
        customResponse.setStatus("Email has been send");
        return customResponse.httpResponse();
    }

    @Autowired
    EnableEmailService enableEmailService;

    @ApiOperation("retrieve email and code to enable email")
    @PatchMapping(value = "/api/enable-email",produces = "application/json")
    public ResponseEntity enableEmail(@Valid @RequestBody EnableEmail enableEmail){
        enableEmailService.enableEmail(enableEmail.getEmail(),enableEmail.getCode());
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @ApiOperation("user sign up with gmail and password")
    @PostMapping(value = "/api/sign-up",produces = "application/json")
    public ResponseEntity signUp(@Valid @RequestBody SignUp signUp){
        signUpService.signUp(signUp);
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @ApiOperation("user login with century 21 cambodia account")
    @PostMapping(value="/api/sign-in",produces = "application/json")
    public ResponseEntity signIn(@Valid @RequestBody SignIn signIn, HttpServletRequest httpServletRequest){
        signInService.emailExist(signIn.getEmail());
        CustomResponse customResponse;
        try {
            HttpResponse<OAuth2> jsonResponse = Unirest.post(Url.oauthTokenUrl)
                    .header("accept", "application/json")
                    .header("Authorization", "Basic YzIxYzoxMjM=")
                    .queryString("grant_type", "password")
                    .queryString("username", signIn.getEmail())
                    .queryString("password", signIn.getPassword())
                    .asObject(OAuth2.class);
            customResponse = new CustomResponse(200, jsonResponse.getBody());
        } catch (UnirestException e) {
            customResponse = new CustomResponse(401);
            customResponse.setStatus("Password not correct.");
            return customResponse.httpResponse();
        }
        return customResponse.httpResponse("result");
    }

    @ApiOperation("sign in with facebook,gmail,wechat")
    @PostMapping(value="/api/social-sign-in",produces = "application/json")
    public ResponseEntity socialSignIn(HttpServletRequest httpServletRequest){
        if(httpServletRequest.getHeader("auth")==null){
            CustomResponse customResponse=new CustomResponse(401);
            return customResponse.httpResponse();
        }
        return socialSignInService.socialSignIn(httpServletRequest.getHeader("auth"));
    }

    @ApiOperation("refresh token")
    @PostMapping(value = "/api/refresh-token",produces = "application/json")
    public ResponseEntity refreshToken(@RequestBody RefreshToken refreshToken){
        CustomResponse customResponse;
        try {
            HttpResponse<OAuth2> jsonResponse = Unirest.post(Url.oauthTokenUrl)
                    .header("accept", "application/json")
                    .header("Authorization", "Basic YzIxYzoxMjM=")
                    .queryString("grant_type", "refresh_token")
                    .queryString("client_id","c21c")
                    .queryString("refresh_token",refreshToken.getToken())
                    .asObject(OAuth2.class);
            customResponse = new CustomResponse(200, jsonResponse.getBody());
        } catch (UnirestException e) {
            customResponse = new CustomResponse(401);
            customResponse.setStatus("Invalid Token.");
            return customResponse.httpResponse();
        }
        return customResponse.httpResponse("result");
    }

    @ApiOperation("user information")
    @GetMapping("/api/user-info")
    public ResponseEntity userInfo(@RequestParam(value = "id")int userID){
        CustomResponse customResponse=new CustomResponse(200,userInfoService.userInfo(userID));
        return customResponse.httpResponse("result");
    }

    @ApiOperation("user question")
    @PostMapping("/api/user-question")
    public ResponseEntity userQuestion(@RequestBody @Valid UserQuestion userQuestion){
        userQuestionService.saveUserQuestion(userQuestion);
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @ApiOperation("user contact")
    @PostMapping("/api/user-contact")
    public ResponseEntity useContact(@RequestBody @Valid UserContact userContact){
        userContactService.saveUserContact(userContact);
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @ApiIgnore
    @ApiOperation("user image")
    @GetMapping("/api/user-image/{fileName:.+}")
    public ResponseEntity userImage(@PathVariable(value = "fileName")String fileName,HttpServletRequest request){
        return fileUploadService.loadFile(fileName,fileUploadProperty.getUserImage(),request);
    }

    @Autowired
    private UserUploadImageService userUploadImageService;

    @ApiOperation("user upload image")
    @PostMapping(value="/api/user-upload-image",consumes ="multipart/form-data",produces = "application/json")
    public ResponseEntity userUploadImage(@RequestParam("userImage")MultipartFile file,@RequestParam("userID")int userID){
        String fileName = fileUploadService.storeImage(file, fileUploadProperty.getUserImage());

        String oldFile = userUploadImageService.findImageName(userID);
        if(oldFile!=null) {
            fileUploadService.removeImage(oldFile, fileUploadProperty.getUserImage());
        }

        userUploadImageService.saveUserImage(userID,fileName);

        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }
}
