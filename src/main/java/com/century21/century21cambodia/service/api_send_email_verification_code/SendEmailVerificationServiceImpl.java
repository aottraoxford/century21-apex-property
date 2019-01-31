package com.century21.century21cambodia.service.api_send_email_verification_code;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_send_email_verification_code.SendEmailVerificationRepo;
import com.century21.century21cambodia.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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

      //  try {

            //read file from mail_template.txt
//            File file = ResourceUtils.getFile("classpath:static/mail_template.txt");
//            InputStream in = new FileInputStream(file);
//            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
//            int i;
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
//            while((i=bufferedReader.read())!=-1) {
//                mailTemplate += (char) i + "";
//            }

            //random number to verify email and save to database
            int code = (int)(Math.random()*8999)+1000;

            //send mail to email
            mailTemplate+=code+"</h1><h6>NOTE: This CODE will be invalid after 2 minutes then u require to request code again.</h6><p>If you don't register for user account with CENTURY 21 CAMBODIA,simply ignore this email. No action will be taken.<h1></h1> Take care.<h1></h1>CENTURY 21 CAMBODIA</p></body></html>";
            mailService.sendMail(email,mailTemplate);

            if(sendEmailVerificationRepo.saveEmailId(email,code)<1){
                throw new CustomRuntimeException(500,"ERROR CODE");
            }
            //wait to minute to remove email verify code
            ScheduledExecutorService scheduledExecutorService= Executors.newSingleThreadScheduledExecutor();
            scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    scheduledExecutorService.shutdown();
                    removeVerifyEmail(email,code);
                }
            }, 120000, 1, TimeUnit.MILLISECONDS);

//        } catch (IOException e) {
//            throw new CustomRuntimeException(500,e.getMessage());
//        }
    }

    @Override
    public void removeVerifyEmail(String email, int code) {
        if(sendEmailVerificationRepo.removeVerifyEmail(email,code)<1){
            throw new CustomRuntimeException(500,"CANT NOT REMOVE VERIFY CODE");
        }
    }
}
