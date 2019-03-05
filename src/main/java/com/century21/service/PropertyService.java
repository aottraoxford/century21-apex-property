package com.century21.service;

import com.century21.repository.PropertyRepo;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;

public interface PropertyService {
    int insertProperty(PropertyRepo.PropertyRequest propertyRequest, Principal principal);
    Map fileUploads(int propertyID, MultipartFile[] galleries, MultipartFile[] doc,Principal principal);
    PropertyRepo.Property findOneProperty(int proID);
}
