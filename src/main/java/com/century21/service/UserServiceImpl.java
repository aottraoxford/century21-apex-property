package com.century21.service;

import com.century21.exception.CustomRuntimeException;
import com.century21.repository.UserRepo;
import com.century21.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private MailService mailService;

    @Override
    public void verification(int code) {
        if(userRepo.updateEnable(code)<1){
            throw new CustomRuntimeException(401,"VERIFY CODE NOT MATCH.");
        }
    }

    @Override
    public void sendMail(String email) {
        if(userRepo.findUserIDByEmail(email)==null) throw new CustomRuntimeException(404,"EMAIL NOT YET REGISTER.");
        String mailTemplate="<html>\n" +
                "\t<head>\n" +
                "\t\t<style>\n" +
                "\t\t\t.code{\n" +
                "\t\t\t\tbackground-color:red;\n" +
                "\t\t\t\tdisplay:inline;\n" +
                "\t\t\t\tfont-weight:bold;\n" +
                "\t\t\t\tcolor:white;\n" +
                "\t\t\t\tfont-family:arial;\n" +
                "\t\t\t}\n" +
                "\t\t</style>\n" +
                "\t</head>\n" +
                "\t<body>\n" +
                "\t    <p>Hi there!</p><h1></h1>\n" +
                "\t    <p>Somebody just tried to reset password for a CENTURY 21 CAMBODIA user account\n" +
                "           using this email address.<h1></h1> To complete the process,\n" +
                "           just copy this code below to verify your email:</p><h1></h1>\n" +
                "\t\t<h1 class='code'>";

        //random number to verify email and save to database
        int code = (int)(Math.random()*89999)+10000;

        //send mail to email
        mailTemplate+=code+"</h1><h6>NOTE: This CODE will be invalid after 2 minutes then u require to request code again.</h6><p>If you don't register for user account with CENTURY 21 CAMBODIA,simply ignore this email. No action will be taken.<h1></h1> Take care.<h1></h1>CENTURY 21 CAMBODIA</p></body></html>";
        mailService.sendMail(email,mailTemplate);
        userRepo.insertVerification(email,code);
    }

    @Override
    public void changePassword(UserRepo.ChangePassword changePassword) {
        if(userRepo.findUserIDByEmail(changePassword.getEmail())==null) throw new CustomRuntimeException(404,"EMAIL NOT EXIST.");
        userRepo.removeCode();
        if(userRepo.checkAccount(changePassword.getEmail())<1){
            throw new CustomRuntimeException(404,"Email not yet verify.");
        }
        userRepo.removeEmail(changePassword.getEmail());
        userRepo.updatePassword(changePassword);
    }
}
