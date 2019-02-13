package com.century21.century21cambodia.service.api_user_reset_verify;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_user_reset_pass.UserResetPassRepo;
import com.century21.century21cambodia.repository.api_user_reset_pass_verify.UserResetVerifyRepo;
import com.century21.century21cambodia.util.MailService;
import com.century21.century21cambodia.util.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserResetPassVerifyServiceImpl implements UserResetPassVerifyService {
    @Autowired
    private MailService mailService;
    @Autowired
    private UserResetVerifyRepo userResetVerifyRepo;
    @Override
    public void sendLinkToEmail(String email,String pass) {
        if(userResetVerifyRepo.checkEmail(email)<1) throw new CustomRuntimeException(400,"Your email not yet register");
        int code = (int)(Math.random()*8999)+1000;
        String codeString = email+"|"+pass+"|"+code;
        String encodeString = Base64.getEncoder().encodeToString(codeString.getBytes());
        userResetVerifyRepo.storeCode(email,code);
        String message ="<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "\t<meta charset=\"utf-8\">\n" +
                "\t<style>\n" +
                "\t\tdiv.title {\n" +
                "\t\t\ttext-align:center;\n" +
                "\t\t\tcolor:white;\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t\tbackground-color:#beaf87;\n" +
                "\t\t\tfloat:left;\n" +
                "\t\t}\n" +
                "\t\tdiv.content {\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t\tfloat:left;\n" +
                "\t\t}\n" +
                "\t\tdiv.content-left{\n" +
                "\t\t\twidth:10%;\n" +
                "\t\t\tfloat:left;\n" +
                "\t\t\theight:10px;\n" +
                "\t\t}\n" +
                "\t\tdiv.content-center {\n" +
                "\t\t\twidth:80%;\n" +
                "\t\t\tfloat:left;\n" +
                "\t\t\theight:100%;\n" +
                "\t\t}\n" +
                "\t\tdiv.center-content{\n" +
                "\t\t\tpadding:5%;\n" +
                "\t\t}\n" +
                "\t\tdiv.content-right{\n" +
                "\t\t\twidth:10%;\n" +
                "\t\t\tfloat:left;\n" +
                "\t\t\theight:10px;\n" +
                "\t\t}\n" +
                "\t\t.center-content a {\n" +
                "\t\t\tbackground-color: #d1c7ab;\n" +
                "\t\t\tcolor:white;\n" +
                "\t\t\ttext-decoration:none;\n" +
                "\t\t\tborder-radius:10px;\n" +
                "\t\t\tpadding-left:10%;\n" +
                "\t\t\tpadding-right:10%;\n" +
                "\t\t\tpadding-top:2%;\n" +
                "\t\t\tpadding-bottom:2%;\n" +
                "\t\t}\n" +
                "\t\t\n" +
                "\t\t.link {\n" +
                "\t\t\tpadding:2%;\n" +
                "\t\t\ttext-align:center;\n" +
                "\t\t}\n" +
                "\t\t\n" +
                "\t\t.link a {\n" +
                "\t\t\tmargin:0 auto;\n" +
                "\t\t}\n" +
                "\t\ta:hover {\n" +
                "\t\t\tbackground-color:#beaf87;\n" +
                "\t\t\tcolor:white;\n" +
                "\t\t}\n" +
                "\t\tdiv.footer{\n" +
                "\t\t\ttext-align:center;\n" +
                "\t\t\tfloat:left;\n" +
                "\t\t\twidth:100%;\n" +
                "\t\t}\n" +
                "\t</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div class=\"wrapper\">\n" +
                "\t\t<div class=\"title\">\n" +
                "\t\t\t<h1>CENTURY 21</h1>\n" +
                "\t\t</div>\n" +
                "\t\t<div class=\"content\">\n" +
                "\t\t\t<div class=\"content-left\"></div>\n" +
                "\t\t\t<div class=\"content-center\">\n" +
                "\t\t\t\t<div class=\"center-content\">\n" +
                "\t\t\t\t\t<h3>\n" +
                "\t\t\t\t\t\tWe got request to reset your CENTURY 21 password.\n" +
                "\t\t\t\t\t</h3>\n" +
                "\t\t\t\t\t<div class=\"link\"> \n" +
                "\t\t\t\t\t\t<a href=\""+Url.host+"api/user/reset/pass/"+encodeString+"\">Reset Password</a>\n" +
                "\t\t\t\t\t</div>\n" +
                "\t\t\t\t\t<h3>\n" +
                "\t\t\t\t\t\tIf you ignore this message, your password won't be changed.\n" +
                "\t\t\t\t\t</h3>\n" +
                "\t\t\t\t\t<h3>\n" +
                "\t\t\t\t\t\tIf you didn't request a password reset, let us know.\n" +
                "\t\t\t\t\t</h3>\n" +
                "\t\t\t\t</div>\n" +
                "\t\t\t</div>\n" +
                "\t\t\t<div class=\"content-right\"></div>\n" +
                "\t\t</div>\n" +
                "\t\t<hr/>\n" +
                "\t\t<div class = \"footer\">\n" +
                "\t\t\t<p>If this not your account you can remove your email from it.</p>\n" +
                "\t\t</div>\n" +
                "\t</div>\n" +
                "</body>\n" +
                "</html>";
        mailService.sendMail(email, message);
    }
}
