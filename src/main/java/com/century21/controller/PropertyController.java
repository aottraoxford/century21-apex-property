package com.century21.controller;

import com.century21.model.response.CustomResponse;
import com.century21.repository.PropertyRepo;
import com.century21.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class PropertyController {
    @Autowired
    private PropertyService propertyService;
    @PostMapping("/api/property/insert")
    public ResponseEntity insertProperty(@RequestBody PropertyRepo.PropertyRequest property){
        CustomResponse customResponse=new CustomResponse(200,propertyService.insertProperty(property));
        return customResponse.httpResponse("property_id");
    }

    @PostMapping("/api/property/file_uploads")
    public ResponseEntity fileUploads(@RequestParam int propertyID, @RequestPart MultipartFile[] galleries, @RequestPart(required = false) MultipartFile[] doc){
        return null;
    }
}
