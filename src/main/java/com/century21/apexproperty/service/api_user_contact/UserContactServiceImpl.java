package com.century21.apexproperty.service.api_user_contact;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.UserRepo;
import com.century21.apexproperty.repository.api_user_contact.UserContactRepo;
import com.century21.apexproperty.repository.api_user_contact.UserContact;
import com.century21.apexproperty.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContactServiceImpl implements UserContactService {
    @Autowired
    private UserContactRepo userContactRepo;
    @Autowired
    private MailService mailService;
    @Autowired
    private UserRepo userRepo;
    @Override
    public void saveUserContact(UserContact userContact) {
        if(userContact.getProjectID()!=null && userContact.getPropertyID()!=null) throw new CustomRuntimeException(400,"project id or property id ,one of them must not use.");
        int id = userContactRepo.saveUserContact(userContact);
        UserRepo.Contact contact=userRepo.findOneContact(id);
        UserRepo.MailAccount mailAccount=userRepo.findOneMailAccount();
        if(contact.getProjectID()!=null){
            contact.setProp(userRepo.findProjectContact(contact.getProjectID()));
        }else if(contact.getPropertyID()!=null){
            contact.setProp(userRepo.findPropertyContact(contact.getPropertyID()));
        }
        String template =   "<p>User name : "+userContact.getName()+"</p>" +
                "<p>Phone : "+userContact.getPhone()+"</p>" +
                "<p>Email : "+userContact.getEmail()+"</p>" +
                "<p>Contact on "+contact.getProp().getType()+" name : "+contact.getProp().getTitle();
        mailService.sendMail("Client Contact Information",mailAccount.getEmail(),template);
    }
}
