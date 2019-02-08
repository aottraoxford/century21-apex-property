package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.configuration.upload.FileUploadProperty;
import com.century21.century21cambodia.configuration.upload.FileUploadService;
import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.model.Pagination;
import com.century21.century21cambodia.repository.api_new_project.Country;
import com.century21.century21cambodia.model.response.CustomResponse;
import com.century21.century21cambodia.repository.api_new_project.Project;
import com.century21.century21cambodia.repository.api_projects.ProjectRepo;
import com.century21.century21cambodia.repository.api_projects.ProjectsRequest;
import com.century21.century21cambodia.repository.api_save_noti.SaveNoti;
import com.century21.century21cambodia.repository.api_update_project.UpdateProj;
import com.century21.century21cambodia.repository.search.SearchParam;
import com.century21.century21cambodia.service.api_allcity.CityService;
import com.century21.century21cambodia.service.api_event_detail.EventDetailService;
import com.century21.century21cambodia.service.api_events.EventsService;
import com.century21.century21cambodia.service.api_get_noti.GetNotiService;
import com.century21.century21cambodia.service.api_modify_event_status.ModifyEventStatusService;
import com.century21.century21cambodia.service.api_new_project.NewProjectService;
import com.century21.century21cambodia.service.api_post_event.PostEventService;
import com.century21.century21cambodia.service.api_project_details.ProjectDetailService;
import com.century21.century21cambodia.service.api_project_related.ProjectRelatedService;
import com.century21.century21cambodia.service.api_projects.ProjectService;
import com.century21.century21cambodia.service.api_projects.ProjectServiceImpl;
import com.century21.century21cambodia.service.api_remove_project_gallery.RemoveProjectGalleryService;
import com.century21.century21cambodia.service.api_save_noti.SaveNotiService;
import com.century21.century21cambodia.service.api_slider.SliderService;
import com.century21.century21cambodia.service.api_slider_add.AddSliderService;
import com.century21.century21cambodia.service.api_slider_update.SliderUpdateService;
import com.century21.century21cambodia.service.api_type_country_project.TypeCountryProjectService;
import com.century21.century21cambodia.service.api_update_project.UpdateProjectService;
import com.century21.century21cambodia.service.api_upload_project_images.ProjectGalleryService;
import com.century21.century21cambodia.service.api_visible_project.VisibleProjectService;
import com.century21.century21cambodia.service.search.SearchService;
import com.century21.century21cambodia.util.Url;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

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
        CustomResponse customResponse=new CustomResponse(200,projectService.projects(project.getCountryID(),project.getProjectTypeID(),pagination),pagination);
        return customResponse.httpResponse("result","paging");
    }

    @ApiOperation("project details")
    @GetMapping(value="/api/project-details",produces = "application/json")
    public ResponseEntity projectDetails(@RequestParam(value = "projectID")int projectID){
        CustomResponse customResponse=new CustomResponse(200,projectDetailService.projectDetails(projectID));
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

