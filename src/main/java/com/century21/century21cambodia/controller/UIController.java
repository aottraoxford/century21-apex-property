package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.repository.api_projects.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@Controller
public class UIController {
    @RequestMapping({"/"})
    public String swagger(){
        return "redirect:/swagger-ui.html";
    }
}
