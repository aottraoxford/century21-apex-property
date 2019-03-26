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
import com.century21.util.MyNotification;
import com.century21.util.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

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
    @Autowired
    private MyNotification myNotification;

    @Override
    public int insertProperty(PropertyRepo.PropertyRequest propertyRequest, Principal principal) {
        Integer userID=userRepo.findUserIDByEmail(principal.getName());
        ID id = new ID();
        propertyRepo.insertProperty(id,propertyRequest,userID);
        int propertyID=id.getId();
        if(propertyRequest.getNeighborhood()!=null) {
            for (int i = 0; i < propertyRequest.getNeighborhood().size(); i++) {
                propertyRequest.getNeighborhood().get(i).setPropertyID(propertyID);
                propertyRepo.insertNeighborhood(propertyRequest.getNeighborhood().get(i));
            }
        }
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
    public List<PropertyRepo.Properties> findAllProperty(Pagination pagination,Principal principal) {
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

    @Override
    public List<PropertyRepo.Properties> findAllPropertyByFilter(PropertyRepo.PropertyFilter filter, Pagination pagination) {
        List<PropertyRepo.Properties> properties = propertyRepo.findAllPropertyByFilter(filter,pagination.getLimit(),pagination.getOffset());
        if(properties.size()<1) throw new CustomRuntimeException(404,"ZERO RESULT");
        pagination.setTotalItem(propertyRepo.findAllPropertyCount());
        return properties;
    }

    @Override
    public void updateStatus(int propertyID, boolean status, Principal principal, HttpServletRequest httpServletRequest) {
        propertyRepo.updateStatus(propertyID,status);
        myNotification.sendToAllSubscriber("New Property Available", "Calculation includes only common costs associated with home ownership. Estimates based on local averages and assumptions that may not apply to you and are provided for informational purposes only.",propertyRepo.findOneGallery(propertyID),httpServletRequest.getHeader("Authorization"),"property",propertyID );
        userLogRepo.insertUserLog("enable property to "+status,userRepo.findUserIDByEmail(principal.getName()));
    }


    @Override
    public List<PropertyRepo.Properties> findAgentProperties(int userID) {
        return propertyRepo.findAgentProperties(userID);
    }

    @Override
    public PropertyRepo.Property updateProperty(PropertyRepo.Property property, Principal principal) {
        propertyRepo.updateProperty(property);
        Collection<Integer> idFromDB=new ArrayList<>();
        idFromDB=propertyRepo.findAllNeighborhoodID(property.getId());
        Collection<Integer> idFromUpdate=new ArrayList<>();
        if(property.getNeighborhoods()!=null && property.getNeighborhoods().size()>0){
            for(int i=0;i<property.getNeighborhoods().size();i++){
                if(propertyRepo.updateNeighborhood(property.getNeighborhoods().get(i))<1){
                    propertyRepo.insertNeighborhood(property.getNeighborhoods().get(i));
                }
                idFromUpdate.add(property.getNeighborhoods().get(i).getId());
            }
            idFromDB.addAll(idFromUpdate);
            idFromDB.removeAll(idFromUpdate);
            for(int i=0;i<idFromDB.size();i++)
                propertyRepo.removeNeighborhood(((List<Integer>) idFromDB).get(i));
        }
        userLogRepo.insertUserLog("update property id="+property.getId(),userRepo.findUserIDByEmail(principal.getName()));
        return propertyRepo.findOneProperty(property.getId());
    }
}
