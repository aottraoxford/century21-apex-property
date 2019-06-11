package com.century21.apexproperty.repository.api_get_noti;

import com.century21.apexproperty.util.Url;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class GetNoti {
    private int id;
    @JsonIgnore
    private int userID;
    private String type;
    private int refID;
    private String title;
    private String message;
    private String image;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

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
        if (image != null) {
            if (type.equalsIgnoreCase("project"))
                return Url.projectThumbnailUrl + image;
            else if (type.equalsIgnoreCase("property"))
                return Url.propertyGalleryUrl + image;
            else if (type.equalsIgnoreCase("event"))
                return Url.bannerUrl + image;
        }
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
