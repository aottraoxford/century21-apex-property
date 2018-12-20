package com.century21.century21cambodia.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Url {
    public static final String oauthTokenUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/oauth/token")
            .toUriString();
}
