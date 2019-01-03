package com.century21.century21cambodia.service.api_user_upload_image;

import org.springframework.stereotype.Service;

@Service
public interface UserUploadImageService {
    String findImageName(int userID);
    void saveUserImage(int userID,String filename);
}
