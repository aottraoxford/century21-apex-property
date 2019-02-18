package com.century21.service.api_save_noti;

import com.century21.exception.CustomRuntimeException;
import com.century21.repository.api_save_noti.SaveNoti;
import com.century21.repository.api_save_noti.SaveNotiRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveNotiServiceImpl implements SaveNotiService {

    @Autowired
    private SaveNotiRepo saveNotiRepo;

    @Override
    public void saveNoti(SaveNoti saveNoti, String email) {
        Integer userID=saveNotiRepo.getUserID(email);
        if(userID==null) throw new CustomRuntimeException(401,"UNAUTHORIZED");
        if(saveNotiRepo.SaveNoti(saveNoti,userID)==null){
            throw new CustomRuntimeException(500,"Unable to save");
        }
    }
}
