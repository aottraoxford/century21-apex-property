package com.century21.service;

import com.century21.model.Pagination;
import com.century21.repository.PropertyRepo;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface PropertyService {
    int insertProperty(PropertyRepo.PropertyRequest propertyRequest, Principal principal);
    Map fileUploads(int propertyID, MultipartFile[] galleries, MultipartFile[] doc,Principal principal);
    PropertyRepo.Property findOneProperty(int proID,Principal principal);
    List<PropertyRepo.Properties> findAllProperty(Pagination pagination);
    void removeFile(int propertyID,String gallName,String docName,Principal principal);
    List<PropertyRepo.Properties> findAllPropertyByFilter(PropertyRepo.PropertyFilter filter,Pagination pagination);
}
