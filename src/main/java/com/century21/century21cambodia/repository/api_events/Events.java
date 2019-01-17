package com.century21.century21cambodia.repository.api_events;

import com.century21.century21cambodia.util.Url;

public class Events {
    private String title;
    private String description;
    private String banner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBanner() {
        if(banner!=null)
            return Url.bannerUrl+banner;
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    @Override
    public String toString() {
        return "Events{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", banner='" + banner + '\'' +
                '}';
    }
}
