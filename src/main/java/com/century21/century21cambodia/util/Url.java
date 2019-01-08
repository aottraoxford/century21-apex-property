package com.century21.century21cambodia.util;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Url {
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
}
