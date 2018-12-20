package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.model.Pagination;
import com.century21.century21cambodia.model.request.Project;
import com.century21.century21cambodia.repository.user_question.UserQuestion;
import com.century21.century21cambodia.model.response.CustomResponse;
import com.century21.century21cambodia.repository.search.SearchParam;
import com.century21.century21cambodia.service.new_project.NewProjectService;
import com.century21.century21cambodia.service.projectdetails.ProjectDetailService;
import com.century21.century21cambodia.service.projects.ProjectService;
import com.century21.century21cambodia.service.search.SearchService;
import com.century21.century21cambodia.service.user_question.UserQuestionService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

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

    @ApiOperation("list all project")
    @PostMapping(value="/api/projects",produces = "application/json")
    public ResponseEntity projects(@RequestBody Project project, @RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "10") int limit){
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

    @ApiOperation("create new project")
    @PostMapping(value = "/api/new-project",produces = "application/json")
    public ResponseEntity newProject(@RequestBody com.century21.century21cambodia.repository.new_project.Project project){
        CustomResponse customResponse=new CustomResponse(200,newProjectService.createNewProject(project));
        return customResponse.httpResponse("project_id");
    }

    @ApiOperation("upload image to project(working only postman)")
    @PostMapping(value = "/api/upload-project-images",produces ={"multipart/form-data"})
    public ResponseEntity uploadProjectImage(@RequestParam("projectID")int projectID,@RequestParam(value = "thumbnail",required = false)MultipartFile thumbnail,@RequestParam(value = "galleries",required = false)MultipartFile[] galleries){

        return null;
    }


}
