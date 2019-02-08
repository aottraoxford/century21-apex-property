package com.century21.century21cambodia.repository.api_event_detail;

public class EventDetail {
    private int id;
    private String banner;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "EventDetail{" +
                "id=" + id +
                ", banner='" + banner + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}