package com.century21.service.api_upload_project_images;

import java.util.List;

public interface ProjectGalleryService {
    GalleryResponse saveProjectImage(String thumbnail, List<String> galleries, int projectID);
    String findThumbnail(int projectID);
}
