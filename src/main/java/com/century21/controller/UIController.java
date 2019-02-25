package com.century21.controller;

import com.century21.repository.ProjectRepo;
import com.century21.service.api_user_reset_pass.UserResetPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springfox.documentation.annotations.ApiIgnore;


@ApiIgnore
@Controller
public class UIController {
    @Autowired
    private ProjectRepo projectRepo;
    @GetMapping({"/"})
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }

    @Autowired
    private UserResetPassService userResetPassService;
    @ApiIgnore
    @GetMapping("api/user/reset/pass/{base64}")
    public String resetPass(@PathVariable("base64")String base64){
        if(userResetPassService.updateUserPassword(base64)) return "redirect:https://www.google.com/";
        else return "redirect:https://www.youtube.com/";

    }
}
