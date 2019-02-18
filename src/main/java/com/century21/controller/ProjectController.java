package com.century21.controller;

import com.century21.configuration.upload.FileUploadProperty;
import com.century21.configuration.upload.FileUploadService;
import com.century21.model.Pagination;
import com.century21.model.response.CustomResponse;
import com.century21.repository.api_new_project.Country;
import com.century21.repository.api_projects.ProjectsRequest;
import com.century21.repository.api_save_noti.SaveNoti;
import com.century21.repository.search.SearchParam;
import com.century21.service.api_allcity.CityService;
import com.century21.service.api_event_detail.EventDetailService;
import com.century21.service.api_events.EventsService;
import com.century21.service.api_get_noti.GetNotiService;
import com.century21.service.api_new_project.NewProjectService;
import com.century21.service.api_project_details.ProjectDetailService;
import com.century21.service.api_project_related.ProjectRelatedService;
import com.century21.service.api_projects.ProjectService;
import com.century21.service.api_save_noti.SaveNotiService;
import com.century21.service.api_slider.SliderService;
import com.century21.service.api_type_country_project.TypeCountryProjectService;
import com.century21.service.search.SearchService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
public class ProjectController {
    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectDetailService projectDetailService;
    @Autowired
    private SearchService searchService;
    @Autowired
    private NewProjectService newProjectService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;

    @ApiOperation("list all project")
    @PostMapping(value="/api/projects",produces = "application/json")
    public ResponseEntity projects(@Valid @RequestBody ProjectsRequest project, @RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "10") int limit){
        Pagination pagination=new Pagination(page,limit);
        CustomResponse customResponse=new CustomResponse(200,projectService.projects(project.getCountryID(),project.getProjectTypeID(),project.isEnable(),pagination),pagination);
        return customResponse.httpResponse("result","paging");
    }

    @ApiOperation("project details")
    @GetMapping(value="/api/project-details",produces = "application/json")
    public ResponseEntity projectDetails(@RequestParam(value = "projectID")int projectID,Principal principal){
        CustomResponse customResponse=new CustomResponse(200,projectDetailService.projectDetails(projectID,principal));
        return customResponse.httpResponse("result");
    }

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


    @ApiOperation("find country")
    @GetMapping(value = "/api/find-countries-by-name",produces = "application/json")
    public ResponseEntity findCountryByName(@RequestParam(value = "name")String name){
        name = "%"+name+"%";
        List<Country> countries=newProjectService.countries(name);
        CustomResponse customResponse=new CustomResponse(200,countries);
        return customResponse.httpResponse("result");
    }

    @ApiIgnore
    @ApiOperation("view project thumbnail")
    @GetMapping("/api/project/thumbnail/{fileName:.+}")
    public ResponseEntity viewProjectThumbnail(@PathVariable("fileName")String fileName, HttpServletRequest request){
        return fileUploadService.loadFile(fileName,fileUploadProperty.getProjectThumbnail(),request);
    }

    @ApiIgnore
    @ApiOperation("view project gallery")
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
    public ResponseEntity relatedProject(@RequestBody ProjectsRequest projectsRequest){
        CustomResponse customResponse=new CustomResponse(200,projectRelatedService.getProjects(projectsRequest.getCountryID(),projectsRequest.getProjectTypeID()));
        return customResponse.httpResponse("result");
    }

    @GetMapping("/api/projects-forweb")
    public ResponseEntity projectsForWeb(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value="limit",defaultValue = "10")int limit){
        CustomResponse customResponse=new CustomResponse(200,projectService.getProjectsFroWeb(page,limit));
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
    private EventsService eventsService;
    @ApiOperation("get all event")
    @GetMapping(value = "api/events",produces = "application/json")
    public ResponseEntity events(){
        CustomResponse customResponse=new CustomResponse(200,eventsService.events());
        return customResponse.httpResponse("result");
    }

    @Autowired
    private EventDetailService eventDetailService;
    @GetMapping(value = "api/event/detail",produces = "application/json")
    public ResponseEntity eventDetail(@RequestParam int eventID){
        CustomResponse customResponse=new CustomResponse(200,eventDetailService.getEventDetail(eventID));
        return customResponse.httpResponse("result");
    }


}

