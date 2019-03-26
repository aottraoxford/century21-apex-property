package com.century21.service;

import com.century21.model.Pagination;
import com.century21.repository.PropertyRepo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface PropertyService {
    int insertProperty(PropertyRepo.PropertyRequest propertyRequest, Principal principal);
    PropertyRepo.Property updateProperty(PropertyRepo.Property property, Principal principal);
    Map fileUploads(int propertyID, MultipartFile[] galleries, MultipartFile[] doc,Principal principal);
    PropertyRepo.Property findOneProperty(int proID,Principal principal);
    List<PropertyRepo.Properties> findAllProperty(Pagination pagination,Principal principal);
    void removeFile(int propertyID,String gallName,String docName,Principal principal);
    List<PropertyRepo.Properties> findAllPropertyByFilter(PropertyRepo.PropertyFilter filter,Pagination pagination);
    void updateStatus(int propertyID, boolean status, Principal principal, HttpServletRequest httpServletRequest);
    List<PropertyRepo.Properties> findAgentProperties(int userID);
}
