package com.century21.service.api_user_contact;

import com.century21.exception.CustomRuntimeException;
import com.century21.repository.api_user_contact.UserContact;
import com.century21.repository.api_user_contact.UserContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContactServiceImpl implements UserContactService {
    @Autowired
    private UserContactRepo userContactRepo;
    @Override
    public void saveUserContact(UserContact userContact) {
        System.out.println(userContact.getPropertyID());
        if(userContact.getProjectID()!=null && userContact.getPropertyID()!=null) throw new CustomRuntimeException(400,"project id or property id ,one of them must not use.");
        int i = userContactRepo.saveUserContact(userContact);
        if(i<1)
            throw new CustomRuntimeException(500,"Can not save.");
    }
}
