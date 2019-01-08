package com.century21.century21cambodia.service.api_remove_project_gallery;

import com.century21.century21cambodia.configuration.upload.FileUploadProperty;
import com.century21.century21cambodia.configuration.upload.FileUploadService;
import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_remove_project_gallery.RemoveProjectGalleryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RemoveProjectGalleryServiceImpl implements RemoveProjectGalleryService {

    @Autowired
    private RemoveProjectGalleryRepo removeProjectGalleryRepo;

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;

    @Override
    public void removeGallery(String imageName) {
        fileUploadService.removeImage(imageName,fileUploadProperty.getProjectGallery());
        if(removeProjectGalleryRepo.removeGallery(imageName)<1){
            throw new CustomRuntimeException(404,"image not found.");
        }
    }
}
