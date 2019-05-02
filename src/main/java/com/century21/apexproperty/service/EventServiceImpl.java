package com.century21.apexproperty.service;

import com.century21.apexproperty.configuration.upload.FileUploadProperty;
import com.century21.apexproperty.configuration.upload.FileUploadService;
import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.model.ID;
import com.century21.apexproperty.model.Pagination;
import com.century21.apexproperty.repository.EventRepo;
import com.century21.apexproperty.util.MyNotification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;
    @Autowired
    private MyNotification myNotification;
    @Autowired
    private EventRepo eventRepo;

    @Override
    public List<EventRepo.EventResponse> findAllEvent(String title, String status, Pagination pagination) {
        if(title!=null){
            title=title.trim().replaceAll(" ","%");
        }
        List<EventRepo.EventResponse> events = eventRepo.findAllEvent(title, status, pagination.getLimit(), pagination.getOffset());

        if (events == null || events.size() < 1) throw new CustomRuntimeException(404, "ZERO RESULT");
        Date today = new Date();
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventDate().compareTo(today) < 1) {
                if (events.get(i).isStatus()) {
                    eventRepo.changeEventStatus(events.get(i).getId(), false);
                    events.remove(i);
                }
            }
        }
        pagination.setTotalItem(eventRepo.findAllEventCount(title, status));
        return events;
    }

    @Override
    public EventRepo.EventResponse findOneEvent(int eventID) {
        EventRepo.EventResponse event = eventRepo.findOneEvent(eventID);
        if (event == null) throw new CustomRuntimeException(404, "ZERO RESULT");
        return event;
    }

    @Override
    public void changeEventStatus(int eventID, boolean status, HttpServletRequest header) {
        EventRepo.EventResponse event = eventRepo.findOneEvent(eventID);
        if (event == null) throw new CustomRuntimeException(404, "ZERO RESULT");
        eventRepo.changeEventStatus(eventID, status);
        if (!event.isStatus() && status) {
            myNotification.sendToAllSubscriber(event.getTitle(), event.getMessage(), event.getBanner(), header.getHeader("Authorization"), "event", eventID);
        }

    }

    @Override
    public EventRepo.EventResponse insertEvent(EventRepo.EventRequest eventRequest) {
        String fileName = fileUploadService.storeImage(eventRequest.getFile(), fileUploadProperty.getEventImage());

        Timestamp date;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date currentDate = new Date();
            if (eventRequest.getEvent_date() != null) {
                Date parsedDate = dateFormat.parse(eventRequest.getEvent_date());
                if (parsedDate.compareTo(currentDate) < 1) {
                    throw new CustomRuntimeException(400, "Input date must be greater than current date");
                }
                date = new Timestamp(parsedDate.getTime());
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(currentDate);
                calendar.add(Calendar.DATE, 5);
                date = new Timestamp(calendar.getTimeInMillis());
            }
        } catch (Exception e) {
            throw new CustomRuntimeException(400, e.getMessage());
        }
        ID id = new ID();
        eventRepo.insertEvent(id, eventRequest, fileName, date);
        return eventRepo.findOneEvent(id.getId());
    }
}
