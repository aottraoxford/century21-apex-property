package com.century21.century21cambodia.service.api_post_event;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_post_event.PostEventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostEventServiceImpl implements PostEventService{
    @Autowired
    private PostEventRepo postEventRepo;
    @Override
    public void postEvent(String title, String description, String banner) {
        if(postEventRepo.postEvent(title,description,banner)<1){
            throw new CustomRuntimeException(400,"Can not post events");
        }
    }
}
