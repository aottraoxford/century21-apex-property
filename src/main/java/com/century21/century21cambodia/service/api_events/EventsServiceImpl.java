package com.century21.century21cambodia.service.api_events;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_events.Events;
import com.century21.century21cambodia.repository.api_events.EventsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EventsServiceImpl implements EventsService {
    @Autowired
    private EventsRepo eventsRepo;
    @Override
    public List<Events> events() {
        List<Events> events=eventsRepo.events();
        if(events==null || events.size()<1){
            throw new CustomRuntimeException(404,"NO RESULT FOR EVENT.");
        }
        for(int i=0;i<events.size();i++){
            if(events.get(i).getDate().compareTo(new Date())<1) {
                eventsRepo.updateEvent(events.get(i).getId());
                events.remove(i);
            }
        }
        return events;
    }
}
