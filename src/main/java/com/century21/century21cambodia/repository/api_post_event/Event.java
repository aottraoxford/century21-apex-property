package com.century21.century21cambodia.repository.api_post_event;

public class Event {
    private String title;
    private String description;
    private String eventDate;

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

    public String getEventDate() {
        //return eventDate;
        return "2019-02-20 10:40";
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "Event{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", eventDate='" + eventDate + '\'' +
                '}';
    }
}
