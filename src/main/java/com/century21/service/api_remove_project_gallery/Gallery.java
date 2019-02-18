package com.century21.service.api_remove_project_gallery;

import java.util.List;

public class Gallery{
    private int projectID;
    private String thumbnail;
    private List<String> galleries;

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getGalleries() {
        return galleries;
    }

    public void setGalleries(List<String> galleries) {
        this.galleries = galleries;
    }

    @Override
    public String toString() {
        return "Gallery{" +
                "projectID=" + projectID +
                ", thumbnail='" + thumbnail + '\'' +
                ", galleries=" + galleries +
                '}';
    }
}
