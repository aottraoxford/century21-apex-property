package com.century21.apexproperty.util;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.UserRepo;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.util.Date;
import java.util.Properties;

@Configuration
public class MailService {

    @Autowired
    private UserRepo userRepo;

    public void sendMail(String subject,String email,String message) {
        UserRepo.MailAccount mailAccount=userRepo.findOneMailAccount();
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
//        properties.put("mail.smtp.host", "smtp.gmail.com");
//        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.host", "c21apex.com");
        properties.put("mail.smtp.port", "587");
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailAccount.getEmail(), mailAccount.getPassword());
            }
        });
//        Session session = Session.getInstance(properties, new Authenticator() {
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("testmailoxford@gmail.com", "!@#$1234qwer");
//            }
//        });
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress(mailAccount.getEmail(), false));
            //msg.setFrom(new InternetAddress("testmailoxford@gmail.com", false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            msg.setSubject(subject);
            msg.setContent(message, "text/html;charset=utf-8");
            msg.setSentDate(new Date());
            Transport.send(msg);
        } catch (MessagingException e) {
            throw new CustomRuntimeException(400,e.getMessage());
        }
    }


}
