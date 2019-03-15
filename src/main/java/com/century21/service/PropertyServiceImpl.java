package com.century21.service;

import com.century21.configuration.upload.FileUploadProperty;
import com.century21.configuration.upload.FileUploadService;
import com.century21.exception.CustomRuntimeException;
import com.century21.model.ID;
import com.century21.model.Pagination;
import com.century21.repository.FavoriteRepo;
import com.century21.repository.PropertyRepo;
import com.century21.repository.UserLogRepo;
import com.century21.repository.UserRepo;
import com.century21.util.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PropertyServiceImpl implements PropertyService{
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;
    @Autowired
    private PropertyRepo propertyRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserLogRepo userLogRepo;
    @Autowired
    private FavoriteRepo favoriteRepo;

    @Override
    public int insertProperty(PropertyRepo.PropertyRequest propertyRequest, Principal principal) {
        Integer userID=userRepo.findUserIDByEmail(principal.getName());
        ID id = new ID();
        propertyRepo.insertProperty(id,propertyRequest,userID);
        int propertyID=id.getId();
        userLogRepo.insertUserLog("insert property id = "+propertyID,userID);
        return propertyID;
    }

    @Override
    public Map fileUploads(int propertyID, MultipartFile[] galleries, MultipartFile[] docs,Principal principal) {
        List<String> gall=fileUploadService.storeImages(galleries,fileUploadProperty.getPropertyGallery());
        List<String> documents;
        if(docs!=null){
            documents=fileUploadService.storeImages(docs,fileUploadProperty.getPropertyDoc());
        }else documents=null;
        Map<String,List<String>> files = new HashMap<>();
        for(int i=0;i<gall.size();i++){
            propertyRepo.insertFiles(gall.get(i),"image",propertyID);
            gall.set(i, Url.propertyGalleryUrl+gall.get(i));
        }
        if(documents!=null){
            for(int i=0;i<documents.size();i++) {
                propertyRepo.insertFiles(documents.get(i),"doc",propertyID);
                documents.set(i, Url.propertyDocsUrl + documents.get(i));
            }
        }
        files.put("galleries",gall);
        files.put("docs",documents);
        Integer userID=userRepo.findUserIDByEmail(principal.getName());
        userLogRepo.insertUserLog("upload file to property id= "+propertyID,userID);
        return files;
    }

    @Override
    public PropertyRepo.Property findOneProperty(int proID,Principal principal) {
        PropertyRepo.Property property=propertyRepo.findOneProperty(proID);
        if(property==null)  throw new CustomRuntimeException(404,"ZERO RESULT");
        if(principal!=null) {
            Integer userID = userRepo.findUserIDByEmail(principal.getName());
            if (favoriteRepo.isFavorite(0, proID, userID) > 0) property.setFavorite(true);
            else property.setFavorite(false);
        }
        return property;
    }

    @Override
    public List<PropertyRepo.Properties> findAllProperty(Pagination pagination) {
        List<PropertyRepo.Properties> properties=propertyRepo.findAllProperty(pagination.getLimit(),pagination.getOffset());
        if(properties==null || properties.size()<1) throw new CustomRuntimeException(404,"ZERO_RESULT");
        pagination.setTotalItem(propertyRepo.findAllPropertyCount());
        return properties;
    }

    @Override
    public void removeFile(int propertyID, String gallName, String docName,Principal principal) {
        if(gallName!=null) {
            fileUploadService.removeImage(gallName, fileUploadProperty.getPropertyGallery());
            propertyRepo.removeFile(propertyID,gallName);
        }
        if(docName!=null) {
            fileUploadService.removeImage(docName, fileUploadProperty.getPropertyDoc());
            propertyRepo.removeFile(propertyID,docName);
        }
        Integer userID=userRepo.findUserIDByEmail(principal.getName());
        userLogRepo.insertUserLog("remove file from property id ="+propertyID,userID);
    }
}
