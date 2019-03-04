package com.century21.controller;

import com.century21.configuration.upload.FileUploadProperty;
import com.century21.configuration.upload.FileUploadService;
import com.century21.model.response.CustomResponse;
import com.century21.repository.PropertyRepo;
import com.century21.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

@RestController
public class PropertyController {
    @Autowired
    private FileUploadProperty fileUploadProperty;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private PropertyService propertyService;
    @PostMapping("/api/property/insert")
    public ResponseEntity insertProperty(@RequestBody PropertyRepo.PropertyRequest property){
        CustomResponse customResponse=new CustomResponse(200,propertyService.insertProperty(property));
        return customResponse.httpResponse("property_id");
    }

    @ApiIgnore
    @GetMapping("/api/property/gallery/{fileName:.+}")
    public ResponseEntity viewPropertyGalleries(@PathVariable("fileName")String fileName, HttpServletRequest request){
        return fileUploadService.loadFile(fileName,fileUploadProperty.getPropertyGallery(),request);
    }

    @ApiIgnore
    @GetMapping("/api/property/doc/{fileName:.+}")
    public ResponseEntity viewPropertyDocs(@PathVariable("fileName")String fileName, HttpServletRequest request){
        return fileUploadService.downloadFile(fileName,fileUploadProperty.getPropertyDoc(),request);
    }

    @PostMapping("/api/property/file_uploads")
    public ResponseEntity fileUploads(@RequestParam int propertyID, @RequestPart MultipartFile[] galleries, @RequestPart(required = false) MultipartFile[] docs){
        CustomResponse customResponse=new CustomResponse(200,propertyService.fileUploads(propertyID,galleries,docs));
        return customResponse.httpResponse("result");
    }

    @GetMapping("/api/property/detail/{id}")
    public ResponseEntity propertyDetail(@PathVariable(name = "id")int id){
        CustomResponse customResponse=new CustomResponse(200,propertyService.findOneProperty(id));
        return customResponse.httpResponse("result");
    }
}
