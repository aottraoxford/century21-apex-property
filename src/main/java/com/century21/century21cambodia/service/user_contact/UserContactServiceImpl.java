package com.century21.century21cambodia.service.user_contact;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.user_contact.UserContact;
import com.century21.century21cambodia.repository.user_contact.UserContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserContactServiceImpl implements UserContactService  {
    @Autowired
    private UserContactRepo userContactRepo;
    @Override
    public void saveUserContact(UserContact userContact) {
        int i = userContactRepo.saveUserContact(userContact);
        if(i<1)
            throw new CustomRuntimeException(500,"Can not save.");
    }
}
