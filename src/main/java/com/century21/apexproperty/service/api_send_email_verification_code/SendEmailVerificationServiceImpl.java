package com.century21.apexproperty.service.api_send_email_verification_code;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_send_email_verification_code.SendEmailVerificationRepo;
import com.century21.apexproperty.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class SendEmailVerificationServiceImpl implements SendEmailVerificationService {

    @Autowired
    private SendEmailVerificationRepo sendEmailVerificationRepo;
    @Autowired
    private MailService mailService;

    @Override
    public void saveEmailId(String email) {

        Date date = sendEmailVerificationRepo.findEmailExpiredDate(email);
        if (date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.SECOND, -15);

            if (calendar.getTime().compareTo(date) <= 0) {
                throw new CustomRuntimeException(400, "Email Send too fast please wait 15 second and try again.");
            }

        }

        //count email in table users that already exists
        int countEmail = sendEmailVerificationRepo.countEmailByEmail(email);
        if (countEmail > 0) throw new CustomRuntimeException(409, "Email already exists");

        //random number to verify email and save to database
        int code = (int) (Math.random() * 89999) + 10000;

        String mailTemplate = "" +
                "<div style=\"height:100px\">" +
                "<img style = \"width:100px;height:100px;float:right;display:block\" src = \"https://app.c21apex.com/api/event/banner/c21apex-icon.png\"/>" +
                "</div>" +
                "<div>" +
                "<p>You have selected " + email + " as your new C21 Apex Property account. To verify this email address belongs to you, enter the code below on the app</p>" +

                "<h1>" + code + "</h1>" +

                "<p>This code will expire in 60 minutes after this email was sent.</p>" +

                "<span style=\"font-weight:bold;font-size:18px\">Why you received this email.</span><br/>" +
                "<p style=\"display:inline\">C21 Apex Property requires verification whenever an email address is selected as an C21 Apex Property account. Your C21 Apex Property account cannot be used until you verify it</p>" +

                "<p>If you did not make this request, you can ignore this email. No C21 Apex Property account will be created without verification.</p>" +

                "<p>C21 Apex Property</p>" +
                "</div>";

        // send mail to email
        mailService.sendMail("CENTURY 21 APEX PROPERTY", email, mailTemplate);

        if (sendEmailVerificationRepo.saveEmailId(email, code) < 1) {
            throw new CustomRuntimeException(500, "ERROR CODE");
        }
    }
}
