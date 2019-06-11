package com.century21.apexproperty.controller;

import com.century21.apexproperty.model.Pagination;
import com.century21.apexproperty.model.response.CustomResponse;
import com.century21.apexproperty.repository.EventRepo;
import com.century21.apexproperty.service.EventService;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping(value = "api/events", produces = "application/json")
    public ResponseEntity events(@ApiParam("search by title") @RequestParam(required = false) String title, @ApiParam(example = "all", allowableValues = "all,true,false") @RequestParam String status, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pagination pagination = new Pagination(page, limit);
        CustomResponse customResponse = new CustomResponse(200, eventService.findAllEvent(title, status, pagination), pagination);
        return customResponse.httpResponse("result", "paging");
    }

    @GetMapping(value = "api/events/detail", produces = "application/json")
    public ResponseEntity detail(@RequestParam int eventID) {
        CustomResponse customResponse = new CustomResponse(200, eventService.findOneEvent(eventID));
        return customResponse.httpResponse("result");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "apis/events/change_status", produces = "application/json")
    public ResponseEntity changeStatus(@RequestParam int eventID, @RequestParam boolean status) {
        eventService.changeEventStatus(eventID, status);
        CustomResponse customResponse = new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @PostMapping(value = "apis/events/insert", produces = "application/json")
    public ResponseEntity insertEvent(@ModelAttribute EventRepo.EventRequest eventRequest) {
        CustomResponse customResponse = new CustomResponse(200, eventService.insertEvent(eventRequest));
        return customResponse.httpResponse("result");
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @DeleteMapping("/apis/event/{id}")
    public ResponseEntity removeEvent(@PathVariable Integer id) {
        eventService.removeEventById(id);
        CustomResponse customResponse = new CustomResponse(200);
        return customResponse.httpResponse();
    }

}
