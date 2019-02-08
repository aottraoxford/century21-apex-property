package com.century21.century21cambodia.service.api_remove_project_gallery;

import org.springframework.web.multipart.MultipartFile;

public interface RemoveProjectGalleryService {
    Gallery removeGallery(int projectID, MultipartFile thumbnail, MultipartFile[] galleries);
}
