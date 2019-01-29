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
import com.century21.century21cambodia.service.api_type_country_project.TypeCountryProjectService;
import com.century21.century21cambodia.service.api_update_project.UpdateProjectService;
import com.century21.century21cambodia.service.api_upload_project_images.ProjectGalleryService;
import com.century21.century21cambodia.service.api_visible_project.VisibleProjectService;
import com.century21.century21cambodia.service.search.SearchService;
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
    public ResponseEntity projects(@RequestBody ProjectsRequest project, @RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "10") int limit){
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

    @ApiOperation("(BACK END)create new project")
    @PostMapping(value = "/api/new-project",produces = "application/json")
    public ResponseEntity newProject(@RequestBody Project project){
        CustomResponse customResponse=new CustomResponse(200,newProjectService.createNewProject(project));
        return customResponse.httpResponse("project_id");
    }

    @Autowired
    private ProjectGalleryService projectGalleryService;

    @ApiOperation("(BACK END)upload image to project(working only postman)")
    @PostMapping(value = "/api/upload-project-images",produces = "application/json")
    public ResponseEntity uploadProjectImage(@RequestParam("projectID")int projectID,@RequestPart(value = "thumbnail",required = false)MultipartFile thumbnail,@RequestPart(value = "galleries",required = false)MultipartFile[] galleries){
        String thum=null;
        String tn=projectGalleryService.findThumbnail(projectID);
        if(thumbnail!=null){
            if(tn!=null) fileUploadService.removeImage(tn,fileUploadProperty.getProjectThumbnail());
            thum = fileUploadService.storeImage(thumbnail,fileUploadProperty.getProjectThumbnail());
        }
        List<String> gall=null;
        if(galleries!=null || galleries.length>0){
            gall = fileUploadService.storeImages(galleries,fileUploadProperty.getProjectGallery());
        }
        CustomResponse customResponse=new CustomResponse(200,projectGalleryService.saveProjectImage(thum,gall,projectID));
        return customResponse.httpResponse("result");
    }

    @ApiOperation("(BACK END)find country")
    @GetMapping(value = "/api/find-countries-by-name",produces = "application/json")
    public ResponseEntity findCountryByName(@RequestParam(value = "name")String name){
        name = "%"+name+"%";
        List<Country> countries=newProjectService.countries(name);
        CustomResponse customResponse=new CustomResponse(200,countries);
        return customResponse.httpResponse("result");
    }

    @Autowired
    private RemoveProjectGalleryService removeProjectGalleryService;

    @ApiOperation("(BACK END)delete one image from project")
    @DeleteMapping(value = "/api/remove-project-gallery",produces = "application/json")
    public ResponseEntity removeProjectGallery(@RequestParam(value = "imageName")String imageName){
        removeProjectGalleryService.removeGallery(imageName);
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
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

    @Autowired
    private UpdateProjectService updateProjectService;

    @ApiOperation("(BACK END)update project")
    @PutMapping(value = "/api/update-project",produces = "application/json",consumes ="application/json")
    public ResponseEntity editProject(@RequestBody UpdateProj updateProj){
        updateProjectService.updateProject(updateProj);
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @Autowired
    private VisibleProjectService visibleProjectService;

    @ApiOperation("(BACK END)visible project")
    @PutMapping(value = "/api/visible-project",produces = "application/json")
    public ResponseEntity visibleProject(@RequestParam("status")boolean status,@RequestParam("projectID")int projectID,HttpServletRequest request){
        visibleProjectService.visibleProject(status,projectID,request.getHeader("Authorization"));
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @Autowired
    private PostEventService postEventService;

    @ApiOperation("(BACK END)post event")
    @PostMapping(value = "/api/post-event",produces = "application/json")
    public ResponseEntity postEvent(@RequestParam("title")String title,@RequestParam("description")String description ,@RequestPart("file") MultipartFile multipartFile){
        postEventService.postEvent(title,description,fileUploadService.storeImage(multipartFile,fileUploadProperty.getEventImage()));
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @Autowired
    private EventsService eventsService;

    @ApiOperation("(BACK END)get all event")
    @GetMapping(value = "/api/events",produces = "application/json")
    public ResponseEntity events(){
        CustomResponse customResponse=new CustomResponse(200,eventsService.events());
        return customResponse.httpResponse("result");
    }

    @Autowired
    private ModifyEventStatusService modifyEventStatusService;

    @ApiOperation("(BACK END) modify event status ")
    @GetMapping(value = "/api/modify-events-status",produces = "application/json")
    public ResponseEntity modifyEventsStatus(@RequestParam("eventID")int eventID,@RequestParam("status")boolean status,HttpServletRequest request){
        modifyEventStatusService.updateStatus(eventID,status,request.getHeader("Authorization"));
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
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

}

