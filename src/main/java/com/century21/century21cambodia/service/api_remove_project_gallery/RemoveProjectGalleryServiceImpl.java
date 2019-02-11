package com.century21.century21cambodia.service.api_remove_project_gallery;

import com.century21.century21cambodia.configuration.upload.FileUploadProperty;
import com.century21.century21cambodia.configuration.upload.FileUploadService;
import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_remove_project_gallery.RemoveProjectGalleryRepo;
import com.century21.century21cambodia.util.ImageUtil;
import com.century21.century21cambodia.util.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class RemoveProjectGalleryServiceImpl implements RemoveProjectGalleryService {

    @Autowired
    private RemoveProjectGalleryRepo removeProjectGalleryRepo;

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;


    @Override
    public Gallery removeGallery(int projectID,String gal, MultipartFile thumbnail, MultipartFile[] galleries) {
        if(removeProjectGalleryRepo.checkID(projectID)<1){
            throw new CustomRuntimeException(404,"Project id NOT FOUND");
        }
        Gallery gallery=new Gallery();
        if(thumbnail!=null){
            fileUploadService.removeImage(thumbnail.getOriginalFilename(),fileUploadProperty.getProjectThumbnail());
            String fileName = fileUploadService.storeImage(thumbnail,fileUploadProperty.getProjectThumbnail());
            removeProjectGalleryRepo.removeThumbnail(projectID,fileName);
            gallery.setProjectID(projectID);
            gallery.setThumbnail(Url.projectThumbnailUrl+ fileName);
        }
        if(gal==null) {
            if (galleries.length > 0) {
                for (int i = 0; i < galleries.length; i++) {
                    fileUploadService.removeImage(galleries[i].getOriginalFilename(), fileUploadProperty.getProjectGallery());
                    removeProjectGalleryRepo.removeGallery(galleries[i].getOriginalFilename());
                }
            }
        }else {
            fileUploadService.removeImage(gal, fileUploadProperty.getProjectGallery());
            removeProjectGalleryRepo.removeGallery(gal);
        }
        List<String> g=removeProjectGalleryRepo.galleries(projectID);
        if(g!=null || g.size()>0){
            for(int i=0;i<g.size();i++){
                g.set(i,Url.projectGalleryUrl+g.get(i));
            }
            gallery.setGalleries(g);
        };
        return gallery;
    }
}
