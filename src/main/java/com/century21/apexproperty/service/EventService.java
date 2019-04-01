package com.century21.apexproperty.service;

import com.century21.apexproperty.model.Pagination;
import com.century21.apexproperty.repository.EventRepo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface EventService {
    List<EventRepo.EventResponse> findAllEvent(String title,String status, Pagination pagination);

    EventRepo.EventResponse findOneEvent(int eventID);

    void changeEventStatus(int eventID, boolean status, HttpServletRequest header);

    EventRepo.EventResponse insertEvent(EventRepo.EventRequest eventRequest);
}
