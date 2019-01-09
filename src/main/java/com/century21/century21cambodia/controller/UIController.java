package com.century21.century21cambodia.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.century21.century21cambodia.model.request.SocialSignIn;
import com.century21.century21cambodia.util.JwtUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

import javax.swing.plaf.synth.SynthScrollBarUI;

@ApiIgnore
@Controller
public class UIController {

    @RequestMapping({"/"})
    public String swagger(){
        return "redirect:/swagger-ui.html";
    }
}
