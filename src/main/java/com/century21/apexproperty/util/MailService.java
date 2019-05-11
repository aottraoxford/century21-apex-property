package com.century21.apexproperty.util;

import com.century21.apexproperty.exception.CustomRuntimeException;
import org.springframework.context.annotation.Configuration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Configuration
public class MailService {

    public void sendMail(String email,String message) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        //properties.put("mail.smtp.host", "c21apex.com");
        //properties.put("mail.smtp.port", "465");
//        Session session = Session.getInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("developer@c21apex.com", "KEXFTGyKng");
//            }
//        });
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("testmailoxford@gmail.com", "!@#$1234qwer");
            }
        });
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("testmailoxford@gmail.com", false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            msg.setSubject("CENTURY 21 CAMBODIA");
            msg.setContent(message, "text/html");
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new CustomRuntimeException(400,e.getMessage());
        }

    }

}
