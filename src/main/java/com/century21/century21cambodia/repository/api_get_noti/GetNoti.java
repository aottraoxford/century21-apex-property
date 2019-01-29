package com.century21.century21cambodia.repository.api_get_noti;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

public class GetNoti {
    @JsonIgnore
    private int id;
    @JsonIgnore
    private int userID;
    @JsonIgnore
    private String type;
    @JsonIgnore
    private int refID;
    private String title;
    private String message;
    private String image;

    public int getRefID() {
        return refID;
    }

    public void setRefID(int refID) {
        this.refID = refID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "GetNoti{" +
                "id=" + id +
                ", userID=" + userID +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
