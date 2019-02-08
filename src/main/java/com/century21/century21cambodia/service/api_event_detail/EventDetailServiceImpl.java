package com.century21.century21cambodia.service.api_event_detail;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_event_detail.EventDetail;
import com.century21.century21cambodia.repository.api_event_detail.EventDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventDetailServiceImpl implements EventDetailService{
    @Autowired
    private EventDetailRepo eventDetailRepo;
    @Override
    public EventDetail getEventDetail(int id) {
        EventDetail eventDetail=eventDetailRepo.getEventDetail(id);
        if(eventDetail==null)
            throw new CustomRuntimeException(404,"ZERO RESULT");
        return eventDetail;
    }
}
