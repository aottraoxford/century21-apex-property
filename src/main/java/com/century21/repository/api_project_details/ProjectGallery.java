package com.century21.repository.api_project_details;

import com.century21.util.Url;

public class ProjectGallery {
    private String image;

    public String getImage() {
        if(image!=null)
            return Url.projectGalleryUrl+image;
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
