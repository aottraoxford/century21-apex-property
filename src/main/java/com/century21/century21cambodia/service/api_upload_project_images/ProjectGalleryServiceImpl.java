package com.century21.century21cambodia.service.api_upload_project_images;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_upload_project_images.ProjectGalleryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProjectGalleryServiceImpl implements ProjectGalleryService {
    @Autowired
    private ProjectGalleryRepo projectGalleryRepo;
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveProjectImage(String thumbnail,List<String> galleries, int projectID) {
        try {
            if(projectGalleryRepo.saveThumbnail(thumbnail,projectID)<1){
                throw new CustomRuntimeException(404,"project id not found.");
            }
            for (int i = 0; i < galleries.size(); i++) {
                projectGalleryRepo.saveGallery(galleries.get(i), projectID);
            }
        }catch (Exception e){
            throw new CustomRuntimeException(500,e.getMessage());
        }
    }

    @Override
    public String findThumbnail(int projectID) {
        return projectGalleryRepo.findThumbnail(projectID);
    }
}
