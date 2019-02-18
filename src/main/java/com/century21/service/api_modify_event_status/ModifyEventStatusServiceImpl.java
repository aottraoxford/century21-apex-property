package com.century21.service.api_modify_event_status;

import com.century21.exception.CustomRuntimeException;
import com.century21.repository.api_modify_event_status.ModifyEventStatusRepo;
import com.century21.util.MyNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyEventStatusServiceImpl implements ModifyEventStatusService {
    @Autowired
    private ModifyEventStatusRepo modifyEventStatusRepo;
    @Autowired
    private MyNotification myNotification;
    @Override
    public void updateStatus(int eventID,boolean status,String token) {
        boolean checkEventStatus=modifyEventStatusRepo.checkEvent(eventID);
        if(modifyEventStatusRepo.updateStatus(eventID,status)<1){
            throw new CustomRuntimeException(404,"event not found");
        }
        String banner = modifyEventStatusRepo.eventImage(eventID);
        if(status==true && !checkEventStatus){
            if(banner!=null){
                banner = banner.trim();
            }
            myNotification.sendToAllSubscriber("aaaa","bbbb",banner,token,"event",eventID);
        }
    }
}
