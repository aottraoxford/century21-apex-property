package com.century21.service.api_remove_project_gallery;

import org.springframework.web.multipart.MultipartFile;

public interface RemoveProjectGalleryService {
    Gallery removeGallery(int projectID, String gallery, MultipartFile thumbnail, MultipartFile[] galleries);
}