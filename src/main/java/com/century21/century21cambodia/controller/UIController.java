package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.repository.api_projects.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDateTime;

@ApiIgnore
@Controller
public class UIController {
    @RequestMapping({"/"})
    public String swagger(){
        LocalDateTime dateTime = LocalDateTime.now();

        System.out.println(toCron(String.valueOf(dateTime.getMinute()),
                String.valueOf(dateTime.getHour()),
                String.valueOf(dateTime.getDayOfMonth()),
                String.valueOf(dateTime.getMonth()),
                String.valueOf(dateTime.getDayOfWeek()), String.valueOf(dateTime.getYear())));
        return "redirect:/swagger-ui.html";
    }
    public static String toCron(final String mins, final String hrs, final String dayOfMonth, final String month, final String dayOfWeek, final String year) {
        return String.format("%s %s %s %s %s %s", mins, hrs, dayOfMonth, month, dayOfWeek, year);
    }
}
