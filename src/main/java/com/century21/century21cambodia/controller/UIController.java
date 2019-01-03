package com.century21.century21cambodia.controller;

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
