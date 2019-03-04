package com.century21.service;

import com.century21.repository.PropertyRepo;
import org.springframework.web.multipart.MultipartFile;

public interface PropertyService {
    int insertProperty(PropertyRepo.PropertyRequest propertyRequest);
    void fileUploads(int propertyID, MultipartFile[] galleries,MultipartFile[] doc);
}
