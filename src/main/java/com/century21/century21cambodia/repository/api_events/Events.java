package com.century21.century21cambodia.repository.api_events;

import com.century21.century21cambodia.util.Url;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Events {
    private int id;
    private String title;
    private String description;
    private String banner;
    @JsonIgnore
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Events{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", banner='" + banner + '\'' +
                ", date=" + date +
                '}';
    }
}
