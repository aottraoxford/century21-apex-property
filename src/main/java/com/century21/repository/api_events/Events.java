package com.century21.repository.api_events;

import com.century21.util.Url;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class Events {
    private int id;
    private String title;
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
                ", banner='" + banner + '\'' +
                ", date=" + date +
                '}';
    }
}
