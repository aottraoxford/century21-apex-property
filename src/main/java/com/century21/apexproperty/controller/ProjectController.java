package com.century21.apexproperty.controller;

import com.century21.apexproperty.model.request.Notification;
import com.century21.apexproperty.service.api_visible_project.VisibleProjectService;
import com.century21.apexproperty.configuration.upload.FileUploadProperty;
import com.century21.apexproperty.configuration.upload.FileUploadService;
import com.century21.apexproperty.model.Pagination;
import com.century21.apexproperty.model.response.CustomResponse;
import com.century21.apexproperty.repository.ProjectRepo;
import com.century21.apexproperty.service.ProjectService;
import com.century21.apexproperty.service.api_allcity.CityService;
import com.century21.apexproperty.service.api_get_noti.GetNotiService;
import com.century21.apexproperty.service.api_project_related.ProjectRelatedService;
import com.century21.apexproperty.service.api_project_statistic.ProjectStatisticService;
import com.century21.apexproperty.service.api_slider.SliderService;
import com.century21.apexproperty.service.api_slider_add.AddSliderService;
import com.century21.apexproperty.service.api_slider_update.SliderUpdateService;
import com.century21.apexproperty.service.api_type_country_project.TypeCountryProjectService;
import com.century21.apexproperty.service.search.SearchService;
import com.century21.apexproperty.util.Url;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Api(value = "project restful api")
@RestController
public class ProjectController{
    @Autowired
    private SearchService searchService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;


    @Autowired
    private VisibleProjectService visibleProjectService;
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/apis/visible-project",produces = "application/json")
    public ResponseEntity visibleProject(@ModelAttribute Notification noti ,
                                         @ApiParam(value = "display project if select true")
                                         @RequestParam("status")boolean status,
                                         @RequestParam(value = "projectID")int projectID){
        visibleProjectService.visibleProject(noti,status,projectID);
        CustomResponse customResponse=new CustomResponse(200);
        return customResponse.httpResponse();
    }


    @Autowired
    private AddSliderService addSliderService;
    //@ApiOperation("(BACK END) add more slide ")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @PostMapping("/apis/slider/add")
    public ResponseEntity addSlider(@RequestParam("title")String name,@RequestPart MultipartFile file){
        String fileName= fileUploadService.storeImage(file,fileUploadProperty.getSlider());
        Integer id=addSliderService.addSlider(name,fileName);
        fileName= Url.sliderUrl+fileName;
        CustomResponse customResponse=new CustomResponse(200,id,fileName);
        return customResponse.httpResponse("slider_id","image");
    }

    @Autowired
    private SliderUpdateService sliderUpdateService;
    //@ApiOperation("(BACK END) update slide ")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @PostMapping("/apis/slider/update")
    public ResponseEntity updateSliders(@RequestParam(value = "enable",defaultValue = "true")boolean enable,@RequestParam("sliderID")int sliderID,@RequestPart(value = "file",required = false)MultipartFile file){
        CustomResponse customResponse=new CustomResponse(200,sliderUpdateService.updateSlider(enable,sliderID,file));
        return customResponse.httpResponse("result");
    }


    @Autowired
    private ProjectStatisticService projectStatisticService;
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @GetMapping("/apis/project/statistic")
    public ResponseEntity projectStatistic(){
        CustomResponse customResponse=new CustomResponse(200,projectStatisticService.statistic());
        return customResponse.httpResponse("result");
    }

    @PostMapping(value="/api/search",produces = "application/json")
    public ResponseEntity search(@RequestBody ProjectRepo.FilterRequest filterRequest, @RequestParam(value = "page",defaultValue = "1")int page, @RequestParam(value = "limit",defaultValue = "10")int limit){
        Pagination pagination=new Pagination(page,limit);
        CustomResponse customResponse=new CustomResponse(200,projectService.filterProject(filterRequest,pagination),pagination);
        return customResponse.httpResponse("result","paging");
    }

    //@ApiOperation("list project types")
    @GetMapping(value = "/api/project-types",produces = "application/json")
    public ResponseEntity projectTypes(){
        CustomResponse customResponse = new CustomResponse(200,searchService.projectTypes());
        return customResponse.httpResponse("result");
    }

    //@ApiOperation("list countries")
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

    //@ApiOperation("get type country project ")
    @GetMapping(value = "/api/type-country-project",produces = "application/json")
    public ResponseEntity typeCountryProject(){
        CustomResponse customResponse=new CustomResponse(200,typeCountryProjectService.typeCP());
        return customResponse.httpResponse("result");
    }


    @Autowired
    private GetNotiService getNotiService;

    @GetMapping(value = "/api/noti",produces = "application/json")
    public ResponseEntity getNoti(@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit){
        Pagination pagination=new Pagination(page,limit);
        CustomResponse customResponse=new CustomResponse(200,getNotiService.getNoti(pagination),pagination);
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
    public ResponseEntity sliders(@RequestParam(required = false) String status,@RequestParam(value = "page",defaultValue = "1")int page,@RequestParam(value = "limit",defaultValue = "10")int limit){
        Pagination pagination=new Pagination(page,limit);
        CustomResponse customResponse=new CustomResponse(200,sliderService.getSlider(status,pagination),pagination);
        return customResponse.httpResponse("result","paging");
    }

    @Autowired
    private ProjectService projectService;
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @PostMapping("/apis/project/insert")
    public ResponseEntity insertProject(@RequestBody ProjectRepo.ProjectRequest project,@ApiIgnore Principal principal){
        CustomResponse customResponse=new CustomResponse(200,projectService.insertProject(project,principal));
        return customResponse.httpResponse("result");
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @PostMapping(value = "/apis/project/image/upload",produces = "application/json", consumes = "multipart/form-data")
    public ResponseEntity uploadProjectImage(@RequestParam("projectID")int projectID, @RequestPart(value = "thumbnail",required = false) MultipartFile thumbnail,@RequestPart(value = "galleries",required = false) MultipartFile[] galleries){
        CustomResponse customResponse=new CustomResponse(200,projectService.uploadProjectImage(thumbnail,galleries,projectID));
        return customResponse.httpResponse("result");
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @DeleteMapping(value = "/apis/project/image/upload",produces = "application/json")
    public ResponseEntity deleteProjectImage(@RequestParam("projectID")int projectID,@RequestParam(value = "gallery",required = false)String galleryName){
        CustomResponse customResponse=new CustomResponse(200,projectService.deleteImage(projectID,galleryName));
        return customResponse.httpResponse("result");
    }

    @GetMapping("/api/project/detail")
    public ResponseEntity projectDetail(@RequestParam int projectID,Principal principal){
        CustomResponse customResponse=new CustomResponse(200,projectService.projectDetail(projectID,principal));
        return customResponse.httpResponse("result");
    }

    @PostMapping("/api/project/listing")
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

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('AGENT')")
    @PutMapping("/apis/project/update")
    public ResponseEntity updateProject(@RequestBody ProjectRepo.ProjectRequest projectRequest){
        CustomResponse customResponse=new CustomResponse(200,projectService.updateProject(projectRequest));
        return customResponse.httpResponse("result");
    }

}

