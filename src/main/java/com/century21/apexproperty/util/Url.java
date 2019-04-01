package com.century21.apexproperty.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Url {
    public static final String host = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/")
            .toUriString();
    public static final String oauthTokenUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/oauth/token")
            .toUriString();
    public static final String projectThumbnailUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/project/thumbnail/")
            .toUriString();
    public static final String projectGalleryUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/project/gallery/")
            .toUriString();
    public static final String userImageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/user/image/")
            .toUriString();
    public static final String bannerUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/event/banner/")
            .toUriString();
    public static final String sliderUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/slider/")
            .toUriString();
    public static final String propertyGalleryUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/property/gallery/")
            .toUriString();
    public static final String propertyDocsUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/property/doc/")
            .toUriString();
}
