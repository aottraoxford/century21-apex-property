package com.century21.century21cambodia.repository.project_details;

public class ProjectGallery {
    private String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProjectGallery{" +
                "image='" + image + '\'' +
                '}';
    }
}
