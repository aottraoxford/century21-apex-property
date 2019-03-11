package com.century21.controller;

import com.century21.configuration.upload.FileUploadProperty;
import com.century21.configuration.upload.FileUploadService;
import com.century21.exception.CustomRuntimeException;
import com.century21.model.Pagination;
import com.century21.model.request.EnableEmail;
import com.century21.model.request.RefreshToken;
import com.century21.model.request.SignIn;
import com.century21.model.request.SignUp;
import com.century21.model.response.CustomResponse;
import com.century21.model.response.OAuth2;
import com.century21.repository.UserRepo;
import com.century21.repository.api_user_contact.UserContact;
import com.century21.repository.api_user_question.UserQuestion;
import com.century21.repository.api_user_update.UpdateInfo;
import com.century21.repository.api_user_upload_image.UserUploadImageRepo;
import com.century21.service.UserService;
import com.century21.service.api_enable_email.EnableEmailService;
import com.century21.service.api_project_favorite.ProjectFavoriteService;
import com.century21.service.api_send_email_verification_code.SendEmailVerificationService;
import com.century21.service.api_signin.SignInService;
import com.century21.service.api_signup.SignUpService;
import com.century21.service.api_social_signin.SocialSignInService;
import com.century21.service.api_user_contact.UserContactService;
import com.century21.service.api_user_favorite.UserFavoriteService;
import com.century21.service.api_user_info.UserInfoService;
import com.century21.service.api_user_question.UserQuestionService;
import com.century21.service.api_user_reset_verify.UserResetPassVerifyService;
import com.century21.service.api_user_update.UserUpdateService;
import com.century21.service.api_user_upload_image.UserUploadImageService;
import com.century21.util.DecodeJWT;
import com.century21.util.JwtUtil;
import com.century21.util.Url;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import java.security.Principal;
import java.util.List;

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

        JwtUtil jwt=new JwtUtil();
        jwt.tokenToObject(httpServletRequest.getHeader("x-auth"), JwtUtil.secret,String.class);

        sendEmailVerificationService.saveEmailId(email);
        CustomResponse customResponse=new CustomResponse(200);
        customResponse.setStatus("Email has been send");
        return customResponse.httpResponse();
    }

    @Autowired
    EnableEmailService enableEmailService;

    @ApiOperation("retrieve email and code to enable email")
    @PatchMapping(value = "/api/enable-email",produces = "application/json")
    public ResponseEntity enableEmail(@Valid @RequestBody EnableEmail enableEmail, HttpServletRequest httpServletRequest){

        JwtUtil jwt=new JwtUtil();
        jwt.tokenToObject(httpServletRequest.getHeader("x-auth"), JwtUtil.secret,String.class);

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
    public ResponseEntity signIn(@Valid @RequestBody SignIn signIn){

        List<String> roles=signInService.emailExist(signIn.getEmail());
        CustomResponse customResponse;
        try {
            HttpResponse<OAuth2> jsonResponse = Unirest.post(Url.oauthTokenUrl)
                    .header("accept", "application/json")
                    .header("Authorization", "Basic YzIxYzoxMjM0NTY3OEBDZW51dHJ5MjFDYW1ib2RpYVJlYWxFc3RhdGU=")
                    .queryString("grant_type", "password")
                    .queryString("username", signIn.getEmail())
                    .queryString("password", signIn.getPassword())
                    .asObject(OAuth2.class);
            customResponse = new CustomResponse(200, jsonResponse.getBody());
            jsonResponse.getBody().setRoles(roles);
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
        return socialSignInService.socialSignIn(httpServletRequest.getHeader("x-auth"));
    }

    @ApiOperation("refresh token")
    @PostMapping(value = "/api/refresh-token",produces = "application/json")
    public ResponseEntity refreshToken(@RequestBody RefreshToken refreshToken){
        CustomResponse customResponse;
        String email = DecodeJWT.getEmailFromJwt(refreshToken.getToken());
        List<String> roles=signInService.emailExist(email);
        try {
            HttpResponse<OAuth2> jsonResponse = Unirest.post(Url.oauthTokenUrl)
                    .header("accept", "application/json")
                    .header("Authorization", "Basic YzIxYzoxMjM0NTY3OEBDZW51dHJ5MjFDYW1ib2RpYVJlYWxFc3RhdGU=")
                    .queryString("grant_type", "refresh_token")
                    .queryString("client_id","c21c")
                    .queryString("refresh_token",refreshToken.getToken())
                    .asObject(OAuth2.class);
            jsonResponse.getBody().setRoles(roles);
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
    public ResponseEntity userInfo(@RequestParam(value = "id",required = false)Integer userID, Principal principal){
        CustomResponse customResponse=new CustomResponse(200,userInfoService.userInfo(userID,principal.getName()));
        return customResponse.httpResponse("result");
    }

    @ApiOperation("user question")
    @PostMapping("/api/user-question")
    public ResponseEntity userQuestion(@RequestBody @Valid UserQuestion userQuestion, HttpServletRequest httpServletRequest){

        userQuestionService.saveUserQuestion(userQuestion);
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @ApiOperation("user contact")
    @PostMapping("/api/user-contact")
    public ResponseEntity useContact(@RequestBody @Valid UserContact userContact, HttpServletRequest httpServletRequest){

        userContactService.saveUserContact(userContact);
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @ApiIgnore
    @ApiOperation("user image")
    @GetMapping("/api/user/image/{fileName:.+}")
    public ResponseEntity userImage(@PathVariable(value = "fileName")String fileName,HttpServletRequest request){
        return fileUploadService.loadFile(fileName,fileUploadProperty.getUserImage(),request);
    }

    @Autowired
    private UserUploadImageService userUploadImageService;
    @Autowired
    private UserUploadImageRepo userUploadImageRepo;
    @ApiOperation("user upload image")
    @PostMapping(value="/api/user-upload-image",produces = "application/json")
    public ResponseEntity userUploadImage(@RequestParam("userImage")MultipartFile file,@RequestParam(value = "userID",required = false)Integer userID,Principal principal){
        if(userID==null){
            userID=userUploadImageRepo.getIDByEmail(principal.getName());
            if(userID==null) throw new CustomRuntimeException(404,"USER NOT EXIST");
        }
        if(userUploadImageRepo.checkUserID(userID)<1) throw new CustomRuntimeException(404,"USER NOT EXIST");
        String fileName = fileUploadService.storeImage(file, fileUploadProperty.getUserImage());
        String oldFile = userUploadImageService.findImageName(userID);
        if(oldFile!=null && !oldFile.contains("/")) {
            fileUploadService.removeImage(oldFile, fileUploadProperty.getUserImage());
        }

        userUploadImageService.saveUserImage(userID,fileName);

        CustomResponse customResponse=new CustomResponse(200, Url.userImageUrl+fileName);
        return customResponse.httpResponse("image");
    }

    @Autowired
    private ProjectFavoriteService projectFavoriteService;
    @PostMapping("api/project/favorite")
    public ResponseEntity projectFavorite(int projectID,Principal principal){
        CustomResponse customResponse=new CustomResponse(200,projectFavoriteService.favorite(projectID,principal));
        return customResponse.httpResponse("favorite");
    }

    @Autowired
    private UserFavoriteService userFavoriteService;
    @GetMapping("api/user/favorite")
    public ResponseEntity userFavorite(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit,Principal principal){
        Pagination pagination=new Pagination(page,limit);
        CustomResponse customResponse=new CustomResponse(200,userFavoriteService.favorite(principal,pagination),pagination);
        return customResponse.httpResponse("result","paging");
    }

    @Autowired
    private UserUpdateService userUpdateService;
    @PostMapping("api/user/update")
    public ResponseEntity userUpdate(@RequestBody UpdateInfo updateInfo, Principal principal){
        CustomResponse customResponse=new CustomResponse(200,userUpdateService.userUpdate(updateInfo,principal));
        return customResponse.httpResponse("result");
    }

    @Autowired
    private UserService userService;
    @GetMapping("api/user/forgotpass/sendmail")
    public ResponseEntity resetVerify(@RequestParam String email){
        userService.sendMail(email);
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }
//
//    @GetMapping("api/user/changepass")
//    public ResponseEntity changePass(@RequestBody UserRepo.ChangePassword changePassword){
//        userService.changePassword(changePassword);
//        CustomResponse customResponse=new CustomResponse(200);
//        return customResponse.httpResponse();
//    }
}
