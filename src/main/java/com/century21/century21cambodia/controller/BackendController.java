package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.configuration.upload.FileUploadProperty;
import com.century21.century21cambodia.configuration.upload.FileUploadService;
import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.model.Pagination;
import com.century21.century21cambodia.model.request.SignIn;
import com.century21.century21cambodia.model.response.CustomResponse;
import com.century21.century21cambodia.repository.api_new_project.Project;
import com.century21.century21cambodia.repository.api_post_event.Event;
import com.century21.century21cambodia.repository.api_update_project.UpdateProj;
import com.century21.century21cambodia.service.api_modify_event_status.ModifyEventStatusService;
import com.century21.century21cambodia.service.api_new_project.NewProjectService;
import com.century21.century21cambodia.service.api_post_event.PostEventService;
import com.century21.century21cambodia.service.api_projects.ProjectService;
import com.century21.century21cambodia.service.api_remove_project_gallery.RemoveProjectGalleryService;
import com.century21.century21cambodia.service.api_slider.SliderService;
import com.century21.century21cambodia.service.api_slider_add.AddSliderService;
import com.century21.century21cambodia.service.api_slider_update.SliderUpdateService;
import com.century21.century21cambodia.service.api_update_project.UpdateProjectService;
import com.century21.century21cambodia.service.api_upload_project_images.ProjectGalleryService;
import com.century21.century21cambodia.service.api_visible_project.VisibleProjectService;
import com.century21.century21cambodia.util.ImageUtil;
import com.century21.century21cambodia.util.Url;
import com.fasterxml.jackson.databind.node.TextNode;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BackendController {

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;

    @Autowired
    private NewProjectService newProjectService;
    @ApiOperation("(BACK END)create new project")
    @PostMapping(value = "/new-project",produces = "application/json")
    public ResponseEntity newProject(@RequestBody Project project){
        CustomResponse customResponse=new CustomResponse(200,newProjectService.createNewProject(project));
        return customResponse.httpResponse("project_id");
    }

    @Autowired
    private ProjectGalleryService projectGalleryService;
    @ApiOperation("(BACK END)upload image to project(working only postman)")
    @PostMapping(value = "/upload-project-images",produces = "application/json")
    public ResponseEntity uploadProjectImage(@RequestParam("projectID")int projectID, @RequestPart(value = "thumbnail",required = false) MultipartFile thumbnail, @RequestPart(value = "galleries",required = false)MultipartFile[] galleries){
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

    @Autowired
    private RemoveProjectGalleryService removeProjectGalleryService;
    @ApiOperation("(BACK END)delete image from project(working only postman)")
    @DeleteMapping(value = "/upload-project-images",produces = "application/json")
    public ResponseEntity deleteProjectImage(@RequestParam("projectID")int projectID,@RequestParam(value = "gallery",required = false)String gallery, @RequestPart(value = "thumbnail",required = false) MultipartFile thumbnail, @RequestPart(value = "galleries",required = false)MultipartFile[] galleries){
        CustomResponse customResponse=new CustomResponse(200,removeProjectGalleryService.removeGallery(projectID,gallery,thumbnail,galleries));
        return customResponse.httpResponse("result");
    }

    @Autowired
    private UpdateProjectService updateProjectService;
    @ApiOperation("(BACK END)update project")
    @PutMapping(value = "/update-project",produces = "application/json",consumes ="application/json")
    public ResponseEntity editProject(@RequestBody UpdateProj updateProj){
        updateProjectService.updateProject(updateProj);
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @Autowired
    private VisibleProjectService visibleProjectService;
    @ApiOperation("(BACK END)visible project")
    @PutMapping(value = "/visible-project",produces = "application/json")
    public ResponseEntity visibleProject(@RequestParam("status")boolean status, @RequestParam("projectID")int projectID, HttpServletRequest request){
        visibleProjectService.visibleProject(status,projectID,request.getHeader("Authorization"));
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @Autowired
    private PostEventService postEventService;
    @ApiOperation("(BACK END)post event")
    @PostMapping(value = "/post-event",produces = "application/json")
    public ResponseEntity postEvent(@RequestPart(value = "file") MultipartFile multipartFile, @ModelAttribute Event event){
        Integer eventID=postEventService.postEvent(event.getTitle(),event.getDescription(),event.getEventDate(),fileUploadService.storeImage(multipartFile,fileUploadProperty.getEventImage()));
        CustomResponse customResponse=new CustomResponse(200,eventID);
        return customResponse.httpResponse("event_id");
    }


    @Autowired
    private ModifyEventStatusService modifyEventStatusService;
    @ApiOperation("(BACK END) modify event status ")
    @GetMapping(value = "/modify-events-status",produces = "application/json")
    public ResponseEntity modifyEventsStatus(@RequestParam("eventID")int eventID,@RequestParam("status")boolean status,HttpServletRequest request){
        modifyEventStatusService.updateStatus(eventID,status,request.getHeader("Authorization"));
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }

    @Autowired
    private AddSliderService addSliderService;
    @ApiOperation("(BACK END) add more slide ")
    @PostMapping("/slider/add")
    public ResponseEntity addSlider(@RequestParam("title")String name,@RequestPart MultipartFile file){
        String fileName= fileUploadService.storeImage(file,fileUploadProperty.getSlider());
        Integer id=addSliderService.addSlider(name,fileName);
        fileName+= Url.sliderUrl;
        CustomResponse customResponse=new CustomResponse(200,id,fileName);
        return customResponse.httpResponse("slider_id","image_path");
    }

    @Autowired
    private SliderUpdateService sliderUpdateService;
    @ApiOperation("(BACK END) update slide ")
    @PostMapping("/slider/update")
    public ResponseEntity updateSliders(@RequestParam(value = "enable",defaultValue = "true")boolean enable,@RequestParam("sliderID")int sliderID,@RequestPart(value = "file",required = false)MultipartFile file){
        CustomResponse customResponse=new CustomResponse(200,sliderUpdateService.updateSlider(enable,sliderID,file));
        return customResponse.httpResponse("result");
    }

    @Autowired
    private ProjectService projectService;
    @GetMapping("/project/all")
    public ResponseEntity listAllProject(@RequestParam("title")String title,@RequestParam(value = "status",required = false)Boolean status ,@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit){
        Pagination pagination=new Pagination(page,limit);
        CustomResponse customResponse=new CustomResponse(200,projectService.listAllProject(title,status,pagination),pagination);
        return customResponse.httpResponse("result","paging");
    }
}
