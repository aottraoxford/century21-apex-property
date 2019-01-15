package com.century21.century21cambodia.controller;

import com.century21.century21cambodia.model.request.SignIn;
import com.century21.century21cambodia.util.MyNotification;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@ApiIgnore
@Controller
public class UIController {


    @RequestMapping({"/"})
    public String swagger(){

        return "redirect:/swagger-ui.html";
    }
}
