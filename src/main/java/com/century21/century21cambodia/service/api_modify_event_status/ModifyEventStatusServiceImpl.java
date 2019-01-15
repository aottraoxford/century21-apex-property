package com.century21.century21cambodia.service.api_modify_event_status;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_modify_event_status.ModifyEventStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModifyEventStatusServiceImpl implements ModifyEventStatusService {
    @Autowired
    private ModifyEventStatusRepo modifyEventStatusRepo;
    @Override
    public void updateStatus(int eventID,boolean status) {
        if(modifyEventStatusRepo.updateStatus(eventID,status)<1){
            throw new CustomRuntimeException(404,"event not found");
        }
    }
}
