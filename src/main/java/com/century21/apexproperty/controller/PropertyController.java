package com.century21.apexproperty.controller;

import com.century21.apexproperty.service.PropertyService;
import com.century21.apexproperty.configuration.upload.FileUploadProperty;
import com.century21.apexproperty.configuration.upload.FileUploadService;
import com.century21.apexproperty.model.Pagination;
import com.century21.apexproperty.model.response.CustomResponse;
import com.century21.apexproperty.repository.PropertyRepo;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
public class PropertyController {
    @Autowired
    private FileUploadProperty fileUploadProperty;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private PropertyService propertyService;

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @PostMapping("/apis/property/insert")
    public ResponseEntity insertProperty(@RequestBody PropertyRepo.PropertyRequest property, @ApiIgnore Principal principal) {
        CustomResponse customResponse = new CustomResponse(200, propertyService.insertProperty(property, principal));
        return customResponse.httpResponse("property_id");
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @PutMapping("/apis/property/update")
    public ResponseEntity updateProperty(@RequestBody PropertyRepo.PropertyUpdate property, @ApiIgnore Principal principal) {
        CustomResponse customResponse = new CustomResponse(200, propertyService.updateProperty(property, principal));
        return customResponse.httpResponse("result");
    }

    @ApiIgnore
    @GetMapping("/api/property/gallery/{fileName:.+}")
    public ResponseEntity viewPropertyGalleries(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        return fileUploadService.loadFile(fileName, fileUploadProperty.getPropertyGallery(), request);
    }

    @ApiIgnore
    @GetMapping("/api/property/doc/{fileName:.+}")
    public ResponseEntity viewPropertyDocs(@PathVariable("fileName") String fileName, HttpServletRequest request) {
        return fileUploadService.downloadFile(fileName, fileUploadProperty.getPropertyDoc(), request);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @PostMapping(value = "/apis/property/file_uploads", consumes = "multipart/form-data")
    public ResponseEntity fileUploads(@RequestParam int propertyID, @ApiParam("galleries file") @RequestPart MultipartFile[] galleries, @ApiParam(value = "document files") @RequestPart(required = false) MultipartFile[] docs, @ApiIgnore Principal principal) {
        CustomResponse customResponse = new CustomResponse(200, propertyService.fileUploads(propertyID, galleries, docs, principal));
        return customResponse.httpResponse("result");
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @DeleteMapping(value = "/apis/property/file_uploads")
    public ResponseEntity fileUploads(@RequestParam int propertyID, @ApiParam("gallery file name") @RequestParam(required = false) String gallName, @ApiParam("document file name") @RequestParam(required = false) String docName, @ApiIgnore Principal principal) {
        propertyService.removeFile(propertyID, gallName, docName, principal);
        CustomResponse customResponse = new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @GetMapping("/api/property/detail/{id}")
    public ResponseEntity propertyDetail(@PathVariable(name = "id") int id, @ApiIgnore Principal principal) {
        CustomResponse customResponse = new CustomResponse(200, propertyService.findOneProperty(id, principal));
        return customResponse.httpResponse("result");
    }

    @PostMapping("/api/property/listing")
    public ResponseEntity properties(@ApiParam("search by title") @RequestParam(required = false) String title, @ApiParam(allowableValues = "all,true,false") @RequestParam(required = false) String status, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit, @ApiIgnore Principal principal) {
        Pagination pagination = new Pagination(page, limit);
        CustomResponse customResponse = new CustomResponse(200, propertyService.findAllProperty(title, status, pagination, principal), pagination);
        return customResponse.httpResponse("result", "paging");
    }

    @PostMapping("/api/property/filter")
    public ResponseEntity propertiesFilter(@RequestBody PropertyRepo.PropertyFilter filter, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pagination pagination = new Pagination(page, limit);
        CustomResponse customResponse = new CustomResponse(200, propertyService.findAllPropertyByFilter(filter, pagination), pagination);
        return customResponse.httpResponse("result", "paging");
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/apis/property/{propertyID}")
    public ResponseEntity updateStatus(@PathVariable(name = "propertyID") int propertyID, @RequestParam boolean status, @ApiIgnore Principal principal, HttpServletRequest httpServletRequest) {
        propertyService.updateStatus(propertyID, status, principal, httpServletRequest);
        CustomResponse customResponse = new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @GetMapping("/apis/property/agent/{userID}")
    public ResponseEntity agentProperties(@PathVariable int userID, @ApiParam(allowableValues = "all,true,false") @RequestParam(required = false) String status, @RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        Pagination pagination = new Pagination(page, limit);
        PropertyRepo.AgentPropertiesStatistic agentPropertiesStatistic = new PropertyRepo.AgentPropertiesStatistic();
        CustomResponse customResponse = new CustomResponse(200, propertyService.findAgentProperties(userID, status, pagination, agentPropertiesStatistic), pagination, agentPropertiesStatistic);
        return customResponse.httpResponse("result", "paging", "statistic");
    }

}
