package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.repository.api_projects.ProjectRepo;
import com.century21.century21cambodia.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@ApiIgnore
@Controller
public class UIController {
    @RequestMapping({"/"})
    public String swagger(){
//        String inputImagePath = "D:/DEVELOP/SPRINGDev/century21cambodia/c21dir/slider/0e14afec-1711-4dbc-ad9a-6ead4483679fGA_EventCoverage_Postcard_Front.jpg";
//        String outputImagePath = "D:/DEVELOP/SPRINGDev/century21cambodia/c21dir/a.jpg";
//        try {
//            // resize to a fixed width (not proportional)
//            int scaledWidth = 1024;
//            int scaledHeight = 768;
//            ImageUtil.resize(inputImagePath, outputImagePath, scaledWidth, scaledHeight);
//
//            double percent = 0.5;
//            ImageUtil.resize(inputImagePath, outputImagePath,percent );
//
//
//        } catch (IOException ex) {
//            System.out.println("Error resizing the image.");
//            ex.printStackTrace();
//        }
        return "redirect:/swagger-ui.html";
    }
}
