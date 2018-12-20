package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.repository.new_project.NewProjectRepo;
import com.century21.century21cambodia.repository.search.SearchParam;
import com.century21.century21cambodia.repository.search.SearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
