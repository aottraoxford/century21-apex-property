package com.century21.service.api_upload_project_images;

import com.century21.util.Url;

import java.util.ArrayList;
import java.util.List;

public class GalleryResponse {
    private String thumbnail;
    private List<String> galleries;

    public String getThumbnail() {
        if(thumbnail!=null)
            return Url.projectThumbnailUrl+thumbnail;
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<String> getGalleries() {
        if(galleries==null || galleries.size()<1) return null;

        List<String> gall = new ArrayList<>();
        for (int i = 0; i < galleries.size(); i++) {
            gall.add(Url.projectGalleryUrl + galleries.get(i));
        }
        return gall;

    }

    public void setGalleries(List<String> galleries) {
        this.galleries = galleries;
    }
}
