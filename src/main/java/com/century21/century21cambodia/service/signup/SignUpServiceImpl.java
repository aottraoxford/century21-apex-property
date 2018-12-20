package com.century21.century21cambodia.service.signup;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.model.request.SignUp;
import com.century21.century21cambodia.repository.signup.SignUpRepo;
import com.century21.century21cambodia.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@org.springframework.stereotype.Service(value = "signUpService")
public class SignUpServiceImpl implements SignUpService {

    @Autowired
    private MailService mailService;
    @Autowired
    private SignUpRepo signupRepo;

    @Autowired
    public SignUpServiceImpl(SignUpRepo signUpRepo){
        this.signupRepo=signUpRepo;
    }


    @Override
    public void saveEmailId(String email) {

        //count email in table users that already exists
        int countEmail = signupRepo.countEmailByEmail(email);
        if(countEmail>0) throw new CustomRuntimeException(409,"Email already exists");

        try {

            //read file from mail_template.txt
            File file = ResourceUtils.getFile("classpath:static/mail_template.txt");
            InputStream in = new FileInputStream(file);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
            int i;
            String mailTemplate="";
            while((i=bufferedReader.read())!=-1) {
                mailTemplate += (char) i + "";
            }

            //random number to verify email and save to database
            int code=(int)(Math.random()*8999)+1000;

            //send mail to email
            mailTemplate+=code+"</h1><h6>NOTE: This CODE will be invalid after 2 minutes then u require to request code again.</h6><p>If you don't register for user account with CENTURY 21 CAMBODIA,simply ignore this email. No action will be taken.<h1></h1> Take care.<h1></h1>CENTURY 21 CAMBODIA</p></body></html>";
            mailService.sendMail(email,mailTemplate);

            if(signupRepo.saveEmailId(email,code)<1){
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

        } catch (IOException e) {
            throw new CustomRuntimeException(500,e.getMessage());
        }
    }

    @Override
    public void enableEmail(String email, int code) {
        if(signupRepo.enableEmail(email,code)<1) throw new CustomRuntimeException(404,"code not match");
    }

    @Override
    public void signUp(SignUp signUp) {
        if(signupRepo.countEmailByEmail(signUp.getEmail())>0){
            throw new CustomRuntimeException(409,"Email already exists");
        }
        if(signupRepo.isEmailVerify(signUp.getEmail())<1){
            throw new CustomRuntimeException(401,"Your email not yet verify");
        }
        if(signupRepo.signUp(signUp)<1){
            throw new CustomRuntimeException(500,"ERROR CODE");
        }
    }

    @Override
    public void removeVerifyEmail(String email, int code) {
        if(signupRepo.removeVerifyEmail(email,code)<1){
            throw new CustomRuntimeException(500,"CANT NOT REMOVE VERIFY CODE");
        }
    }
}
