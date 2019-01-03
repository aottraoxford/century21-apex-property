package com.century21.century21cambodia.service.api_upload_project_images;

import java.util.List;

public interface ProjectGalleryService {
    void saveProjectImage(String thumbnail, List<String> galleries, int projectID);
    String findThumbnail(int projectID);
}
