package com.century21.apexproperty.configuration.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileUploadProperty {
    private String userImage;
    private String projectThumbnail;
    private String projectGallery;
    private String eventImage;
    private String slider;
    private String propertyGallery;
    private String propertyDoc;

    public String getPropertyGallery() {
        return propertyGallery;
    }

    public void setPropertyGallery(String propertyGallery) {
        this.propertyGallery = propertyGallery;
    }

    public String getPropertyDoc() {
        return propertyDoc;
    }

    public void setPropertyDoc(String propertyDoc) {
        this.propertyDoc = propertyDoc;
    }

    public String getSlider() {
        return slider;
    }

    public void setSlider(String slider) {
        this.slider = slider;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getProjectThumbnail() {
        return projectThumbnail;
    }

    public void setProjectThumbnail(String projectThumbnail) {
        this.projectThumbnail = projectThumbnail;
    }

    public String getProjectGallery() {
        return projectGallery;
    }

    public void setProjectGallery(String projectGallery) {
        this.projectGallery = projectGallery;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }
}
