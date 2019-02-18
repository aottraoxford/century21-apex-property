package com.century21.configuration.upload;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileUploadProperty {
    private String userImage;
    private String projectThumbnail;
    private String projectGallery;
    private String eventImage;
    private String slider;

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

    @Override
    public String toString() {
        return "FileUploadProperty{" +
                "userImage='" + userImage + '\'' +
                ", projectThumbnail='" + projectThumbnail + '\'' +
                ", projectGallery='" + projectGallery + '\'' +
                ", eventImage='" + eventImage + '\'' +
                ", slider='" + slider + '\'' +
                '}';
    }
}
