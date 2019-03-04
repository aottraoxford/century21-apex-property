package com.century21.service;

import com.century21.repository.PropertyRepo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface PropertyService {
    int insertProperty(PropertyRepo.PropertyRequest propertyRequest);
    Map fileUploads(int propertyID, MultipartFile[] galleries, MultipartFile[] doc);
    PropertyRepo.Property findOneProperty(int proID);
}
