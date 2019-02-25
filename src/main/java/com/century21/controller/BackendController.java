package com.century21.controller;

import com.century21.configuration.upload.FileUploadProperty;
import com.century21.configuration.upload.FileUploadService;
import com.century21.model.response.CustomResponse;
import com.century21.service.api_project_statistic.ProjectStatisticService;
import com.century21.service.api_slider_add.AddSliderService;
import com.century21.service.api_slider_update.SliderUpdateService;
import com.century21.service.api_visible_project.VisibleProjectService;
import com.century21.util.Url;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BackendController {

    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;


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
    private AddSliderService addSliderService;
    @ApiOperation("(BACK END) add more slide ")
    @PostMapping("/slider/add")
    public ResponseEntity addSlider(@RequestParam("title")String name,@RequestPart MultipartFile file){
        String fileName= fileUploadService.storeImage(file,fileUploadProperty.getSlider());
        Integer id=addSliderService.addSlider(name,fileName);
        fileName= Url.sliderUrl+fileName;
        CustomResponse customResponse=new CustomResponse(200,id,fileName);
        return customResponse.httpResponse("slider_id","image");
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
    private ProjectStatisticService projectStatisticService;
    @GetMapping("/project/statistic")
    public ResponseEntity projectStatistic(){
        CustomResponse customResponse=new CustomResponse(200,projectStatisticService.statistic());
        return customResponse.httpResponse("result");
    }
}
