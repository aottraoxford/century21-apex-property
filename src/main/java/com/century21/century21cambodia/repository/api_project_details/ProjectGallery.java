package com.century21.century21cambodia.repository.api_project_details;

import com.century21.century21cambodia.util.Url;

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
