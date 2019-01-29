package com.century21.century21cambodia.repository.search;


import com.century21.century21cambodia.util.Url;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Project {
    private Integer id;
    private String name;
    @JsonProperty("start_price")
    private Double startPrice;
    @JsonProperty("end_price")
    private Double endPrice;
    private Double grr;
    private String country;
    @JsonProperty("project_type")
    private String projectType;
    private String thumbnail;

    public Integer getId() {
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

    public Double getGrr() {
        return grr;
    }

    public void setGrr(Double grr) {
        this.grr = grr;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getThumbnail() {
        if(thumbnail!=null)
            return Url.projectThumbnailUrl+thumbnail;
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startPrice=" + startPrice +
                ", endPrice=" + endPrice +
                ", grr=" + grr +
                ", country='" + country + '\'' +
                ", projectType='" + projectType + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                '}';
    }
}
