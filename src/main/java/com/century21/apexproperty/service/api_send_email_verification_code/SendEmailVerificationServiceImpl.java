package com.century21.apexproperty.service.api_send_email_verification_code;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_send_email_verification_code.SendEmailVerificationRepo;
import com.century21.apexproperty.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendEmailVerificationServiceImpl implements SendEmailVerificationService {
    @Autowired
    private SendEmailVerificationRepo sendEmailVerificationRepo;
    @Autowired
    private MailService mailService;
    @Override
    public void saveEmailId(String email) {
        //count email in table users that already exists
        int countEmail = sendEmailVerificationRepo.countEmailByEmail(email);
        if(countEmail>0) throw new CustomRuntimeException(409,"Email already exists");

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
                    "\t    <p>Somebody just tried to register for a CENTURY 21 CAMBODIA user account\n" +
                    "           using this email address.<h1></h1> To complete the registration process,\n" +
                    "           just copy this code below to verify your email:</p><h1></h1>\n" +
                    "\t\t<h1 class='code'>";

            //random number to verify email and save to database
            int code = (int)(Math.random()*89999)+10000;

            //send mail to email
            mailTemplate+=code+"</h1><h6>NOTE: This CODE will be invalid after 2 minutes then u require to request code again.</h6><p>If you don't register for user account with CENTURY 21 CAMBODIA,simply ignore this email. No action will be taken.<h1></h1> Take care.<h1></h1>CENTURY 21 CAMBODIA</p></body></html>";
            mailService.sendMail(email,mailTemplate);

            if(sendEmailVerificationRepo.saveEmailId(email,code)<1){
                throw new CustomRuntimeException(500,"ERROR CODE");
            }
    }
}
