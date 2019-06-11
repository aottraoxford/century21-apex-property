package com.century21.apexproperty.repository.api_project_related;


import com.century21.apexproperty.util.Url;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Project {
    private int id;
    private String name;
    private double price;
    private double grr;
    private String country;
    private String description;
    @JsonProperty("project_type")
    private String projectType;
    private String thumbnail;
    @JsonProperty("rent_or_buy")
    private String rentOrBuy;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getGrr() {
        return grr;
    }

    public void setGrr(double grr) {
        this.grr = grr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getThumbnail() {
        if (thumbnail != null)
            return Url.projectThumbnailUrl + thumbnail;
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getRentOrBuy() {
        return rentOrBuy;
    }

    public void setRentOrBuy(String rentOrBuy) {
        this.rentOrBuy = rentOrBuy;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
