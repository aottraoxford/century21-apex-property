package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.repository.api_project_userfavorite.ProjectFavoriteRepo;
import com.century21.century21cambodia.repository.api_projects.ProjectRepo;
import com.century21.century21cambodia.service.api_user_reset_pass.UserResetPassService;
import com.century21.century21cambodia.util.ImageUtil;
import com.century21.century21cambodia.util.JwtUtil;
import com.century21.century21cambodia.util.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceHandlerMethodArgumentResolver;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
        return "redirect:/swagger-ui.html";
    }

    @Autowired
    private UserResetPassService userResetPassService;
    @GetMapping("api/user/reset/pass/{base64}")
    public String resetPass(@PathVariable("base64")String base64){
        if(userResetPassService.updateUserPassword(base64)) return "redirect:https://www.google.com/";
        else return "redirect:https://www.youtube.com/";

    }
}
