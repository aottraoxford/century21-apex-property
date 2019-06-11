package com.century21.apexproperty.service.api_user_upload_image;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_user_upload_image.UserUploadImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserUploadImageServiceImpl implements UserUploadImageService {

    @Autowired
    private UserUploadImageRepo userUploadImageRepo;

    @Override
    public String findImageName(int userID) {
        return userUploadImageRepo.findImageName(userID);
    }

    @Override
    public void saveUserImage(int userID, String filename) {
//        if(userUploadImageRepo.getAccountType(userID)==null || userUploadImageRepo.getAccountType(userID)!="origin" )
//            filename= Url.userImageUrl+filename;
        if (userUploadImageRepo.saveUserImage(userID, filename) == null) {
            throw new CustomRuntimeException(400, "user not exists");
        }

    }

}
