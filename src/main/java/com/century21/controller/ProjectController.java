package com.century21.controller;

import com.century21.configuration.upload.FileUploadProperty;
import com.century21.configuration.upload.FileUploadService;
import com.century21.model.Pagination;
import com.century21.model.response.CustomResponse;
import com.century21.repository.ProjectRepo;
import com.century21.repository.api_save_noti.SaveNoti;
import com.century21.repository.search.SearchParam;
import com.century21.service.ProjectService;
import com.century21.service.api_allcity.CityService;
import com.century21.service.api_get_noti.GetNotiService;
import com.century21.service.api_project_related.ProjectRelatedService;
import com.century21.service.api_save_noti.SaveNotiService;
import com.century21.service.api_slider.SliderService;
import com.century21.service.api_type_country_project.TypeCountryProjectService;
import com.century21.service.search.SearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
public class ProjectController {
    @Autowired
    private SearchService searchService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;



    @ApiOperation("search project")
    @PostMapping(value="/api/search",produces = "application/json")
    public ResponseEntity search(@RequestBody @Valid SearchParam searchParam, @RequestParam(value = "page",defaultValue = "1")int page, @RequestParam(value = "limit",defaultValue = "10")int limit){
        Pagination pagination=new Pagination(page,limit);
        CustomResponse customResponse=new CustomResponse(200,searchService.search(searchParam,pagination),pagination);
        return customResponse.httpResponse("result","paging");
    }

    @ApiOperation("list project types")
    @GetMapping(value = "/api/project-types",produces = "application/json")
    public ResponseEntity projectTypes(){
        CustomResponse customResponse = new CustomResponse(200,searchService.projectTypes());
        return customResponse.httpResponse("result");
    }

    @ApiOperation("list countries")
    @GetMapping(value = "/api/countries",produces = "application/json")
    public ResponseEntity countries(){
        CustomResponse customResponse=new CustomResponse(200,searchService.countries());
        return customResponse.httpResponse("result");
    }

    @ApiIgnore
    @GetMapping("/api/project/thumbnail/{fileName:.+}")
    public ResponseEntity viewProjectThumbnail(@PathVariable("fileName")String fileName, HttpServletRequest request){
        return fileUploadService.loadFile(fileName,fileUploadProperty.getProjectThumbnail(),request);
    }

    @ApiIgnore
    @GetMapping("/api/project/gallery/{fileName:.+}")
    public ResponseEntity viewProjectGallery(@PathVariable("fileName")String fileName, HttpServletRequest request){
        return fileUploadService.loadFile(fileName,fileUploadProperty.getProjectGallery(),request);
    }

    @ApiIgnore
    @GetMapping("/api/event/banner/{fileName:.+}")
    public ResponseEntity viewBanner(@PathVariable("fileName")String fileName, HttpServletRequest request){
        return fileUploadService.loadFile(fileName,fileUploadProperty.getEventImage(),request);
    }

    @Autowired
    private TypeCountryProjectService typeCountryProjectService;

    @ApiOperation("get type country project ")
    @GetMapping(value = "/api/type-country-project",produces = "application/json")
    public ResponseEntity typeCountryProject(){
        CustomResponse customResponse=new CustomResponse(200,typeCountryProjectService.typeCP());
        return customResponse.httpResponse("result");
    }

    @Autowired
    private SaveNotiService saveNotiService;

    @ApiIgnore
    @PostMapping(value = "/api/save-noti",produces = "application/json")
    public ResponseEntity saveNoti(@RequestBody SaveNoti saveNoti, Principal principal){
        saveNotiService.saveNoti(saveNoti, principal.getName());
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @Autowired
    private GetNotiService getNotiService;

    @GetMapping(value = "/api/get-noti",produces = "application/json")
    public ResponseEntity getNoti(Principal principal,@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit){
        Pagination pagination=new Pagination(page,limit);
        CustomResponse customResponse=new CustomResponse(200,getNotiService.getNoti(principal.getName()),pagination);
        return customResponse.httpResponse("result","paging");
    }

    @Autowired
    private ProjectRelatedService projectRelatedService;

    @PostMapping(value = "/api/project/related",produces = "application/json")
    public ResponseEntity relatedProject(@RequestBody ProjectRepo.ProjectRequest projectsRequest){
        CustomResponse customResponse=new CustomResponse(200,projectRelatedService.getProjects(projectsRequest.getCountryID(),projectsRequest.getProjectTypeID()));
        return customResponse.httpResponse("result");
    }

    @Autowired
    private CityService cityService;
    @GetMapping("/api/project/city")
    public ResponseEntity cities(){
        CustomResponse customResponse=new CustomResponse(200,cityService.allCity());
        return customResponse.httpResponse("result");
    }


    @ApiIgnore
    @GetMapping("/api/slider/{fileName:.+}")
    public ResponseEntity viewSlider(@PathVariable("fileName")String fileName, HttpServletRequest request){
        return fileUploadService.loadFile(fileName,fileUploadProperty.getSlider(),request);
    }

    @Autowired
    private SliderService sliderService;
    @GetMapping("/api/slider")
    public ResponseEntity sliders(@RequestParam(defaultValue = "true") boolean enable){
        CustomResponse customResponse=new CustomResponse(200,sliderService.getSlider(enable));
        return customResponse.httpResponse("result");
    }

    @Autowired
    private ProjectService projectService;
    @PostMapping("/api/project/insert")
    public ResponseEntity insertProject(@RequestBody ProjectRepo.ProjectRequest project){
        CustomResponse customResponse=new CustomResponse(200,projectService.insertProject(project));
        return customResponse.httpResponse("result");
    }

    @PostMapping(value = "/api/project/image/upload",produces = "application/json")
    public ResponseEntity uploadProjectImage(@RequestParam("projectID")int projectID, @RequestPart(value = "thumbnail",required = false) MultipartFile thumbnail, @RequestPart(value = "galleries",required = false)MultipartFile[] galleries){
        CustomResponse customResponse=new CustomResponse(200,projectService.uploadProjectImage(thumbnail,galleries,projectID));
        return customResponse.httpResponse("result");
    }

    @DeleteMapping(value = "/api/project/image/upload",produces = "application/json")
    public ResponseEntity deleteProjectImage(@RequestParam("projectID")int projectID,@RequestParam(value = "gallery",required = false)String galleryName){
        CustomResponse customResponse=new CustomResponse(200,projectService.deleteImage(projectID,galleryName));
        return customResponse.httpResponse("result");
    }

    @GetMapping("/api/project/detail")
    public ResponseEntity projectDetail(@RequestParam int projectID,Principal principal){
        CustomResponse customResponse=new CustomResponse(200,projectService.projectDetail(projectID,principal));
        return customResponse.httpResponse("result");
    }

    @GetMapping("/api/project/listing")
    public ResponseEntity projectListing(@RequestParam(required = false) String title,@RequestBody ProjectRepo.ProjectListingRequest projectListingRequest,@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value="limit",defaultValue = "10")int limit){
        Pagination pagination=new Pagination(page,limit);
        CustomResponse customResponse=new CustomResponse(200,projectService.projects(title,projectListingRequest.getCountryID(),projectListingRequest.getProjectTypID(),projectListingRequest.getStatus(),pagination),pagination);
        return customResponse.httpResponse("result","paging");
    }

    @GetMapping("/api/project/listing/web")
    public ResponseEntity projectListingForWeb(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value="limit",defaultValue = "10")int limit){
        CustomResponse customResponse=new CustomResponse(200,projectService.projectsFroWeb(page,limit));
        return customResponse.httpResponse("result");
    }

    @PutMapping("/api/project/update")
    public ResponseEntity updateProject(@RequestBody ProjectRepo.ProjectRequest projectRequest){
        CustomResponse customResponse=new CustomResponse(200,projectService.updateProject(projectRequest));
        return customResponse.httpResponse("result");
    }



}

