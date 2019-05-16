package com.century21.apexproperty.service.api_send_email_verification_code;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_send_email_verification_code.SendEmailVerificationRepo;
import com.century21.apexproperty.util.MailService;
import com.century21.apexproperty.util.Url;
import org.apache.http.client.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SendEmailVerificationServiceImpl implements SendEmailVerificationService {
    @Autowired
    private SendEmailVerificationRepo sendEmailVerificationRepo;
    @Autowired
    private MailService mailService;
    @Override
    public void saveEmailId(String email) {

        Date date=sendEmailVerificationRepo.findEmailExpiredDate(email);
        if(date!=null){
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.SECOND,-15);

            if (calendar.getTime().compareTo(date) <= 0) {
                throw new CustomRuntimeException(400, "Email Send too fast please wait 15 second and try again.");
            }

        }

        //count email in table users that already exists
        int countEmail = sendEmailVerificationRepo.countEmailByEmail(email);
        if(countEmail>0) throw new CustomRuntimeException(409,"Email already exists");

//            String mailTemplate="<html>\n" +
//                    "\t<head>\n" +
//                    "\t\t<style>\n" +
//                    "\t\t\t.code{\n" +
//                    "\t\t\t\tbackground-color:red;\n" +
//                    "\t\t\t\tdisplay:inline;\n" +
//                    "\t\t\t\tfont-weight:bold;\n" +
//                    "\t\t\t\tcolor:white;\n" +
//                    "\t\t\t\tfont-family:arial;\n" +
//                    "\t\t\t}\n" +
//                    "\t\t</style>\n" +
//                    "\t</head>\n" +
//                    "\t<body>\n" +
//                    "\t    <p>Hi there!</p><h1></h1>\n" +
//                    "\t    <p>Somebody just tried to register for a CENTURY 21 CAMBODIA user account\n" +
//                    "           using this email address.<h1></h1> To complete the registration process,\n" +
//                    "           just copy this code below to verify your email:</p><h1></h1>\n" +
//                    "\t\t<h1 class='code'>";

        //random number to verify email and save to database
        int code = (int)(Math.random()*89999)+10000;

        String mailTemplate = "" +
                "<div style=\"height:100px\">" +
                "<img style = \"width:100px;height:100px;float:right;display:block\" src = \""+ Url.bannerUrl +"icon.jpg\"/>" +
                "</div>"+
                "<div>"+
                "<p>You have selected "+email+" as your new C21 Apex Property account. To verify this email address belongs to you, enter the code below on the app</p>"+

                "<h1>"+code+"</h1>"+

                "<p>This code will expire 60 minutes after this email was sent.</p>"+

                "<span style=\"font-weight:bold;font-size:18px\">Why you received this email.</span></br>"+
                "<p style=\"display:inline\">C21 Apex Property requires verification whenever an email address is selected as an C21 Apex Property account. Your C21 Apex Property account cannot be used until you verify it</p>"+

                "<p>If you did not make this request, you can ignore this email. No C21 Apex Property account will be created without verification.</p>"+

                "<p>C21 Apex Property</p>"+
                "</div>";



//            //send mail to email
//            mailTemplate+=code+"</h1><h6>NOTE: This CODE will be invalid after 15 minutes then u require to request code again.</h6><p>If you don't register for user account with CENTURY 21 CAMBODIA,simply ignore this email. No action will be taken.<h1></h1> Take care.<h1></h1>CENTURY 21 CAMBODIA</p></body></html>";
//            mailService.sendMail(email,mailTemplate);

        if(sendEmailVerificationRepo.saveEmailId(email,code)<1){
            throw new CustomRuntimeException(500,"ERROR CODE");
        }
    }
}
