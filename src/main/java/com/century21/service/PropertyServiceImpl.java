package com.century21.service;

import com.century21.configuration.upload.FileUploadProperty;
import com.century21.configuration.upload.FileUploadService;
import com.century21.model.ID;
import com.century21.repository.PropertyRepo;
import com.century21.util.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    @Override
    public int insertProperty(PropertyRepo.PropertyRequest propertyRequest) {
        ID id = new ID();
        propertyRepo.insertProperty(id,propertyRequest);
        return id.getId();
    }

    @Override
    public void fileUploads(int propertyID, MultipartFile[] galleries, MultipartFile[] docs) {
        List<String> gall=fileUploadService.storeImages(galleries,fileUploadProperty.getPropertyGallery());
        List<String> documents;
        if(docs!=null){
            documents=fileUploadService.storeImages(docs,fileUploadProperty.getPropertyDoc());
        }else documents=null;
        Map<String,List<String>> files = new HashMap<>();
        for(int i=0;i<gall.size();i++){
            gall.set(i, Url.propertyGalleryUrl+gall.get(i));
        }
        if(documents!=null){
            for(int i=0;i<documents.size();i++)
                documents.set(i,Url.propertyDocsUrl+documents.get(i));
        }
        files.put("galleries",gall);
        files.put("docs",documents);
    }
}
