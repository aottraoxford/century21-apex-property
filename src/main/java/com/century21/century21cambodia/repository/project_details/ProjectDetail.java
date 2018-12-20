package com.century21.century21cambodia.repository.project_details;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProjectDetail {
    private String title;
    private String country;
    @JsonProperty("address_#1")
    private String addressOne;
    @JsonProperty("address_#2")
    private String addressTwo;
    @JsonProperty("start_price")
    private double minPrice;
    @JsonProperty("end_price")
    private double maxPrice;
    @JsonProperty("avg_annual_rent_from")
    private double averageAnnualRentFrom;
    @JsonProperty("avg_annual_rent_to")
    private double averageAnnualRentTo;
    @JsonProperty("down_payment")
    private String downPayment;
    @JsonProperty("project_type")
    private String projectType;
    private String description;
    @JsonProperty("introductions")
    private List<ProjectIntro> projectIntro;
    @JsonProperty("galleries")
    private List<ProjectGallery> projectGalleries;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddressOne() {
        return addressOne;
    }

    public void setAddressOne(String addressOne) {
        this.addressOne = addressOne;
    }

    public String getAddressTwo() {
        return addressTwo;
    }

    public void setAddressTwo(String addressTwo) {
        this.addressTwo = addressTwo;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getAverageAnnualRentFrom() {
        return averageAnnualRentFrom;
    }

    public void setAverageAnnualRentFrom(double averageAnnualRentFrom) {
        this.averageAnnualRentFrom = averageAnnualRentFrom;
    }

    public double getAverageAnnualRentTo() {
        return averageAnnualRentTo;
    }

    public void setAverageAnnualRentTo(double averageAnnualRentTo) {
        this.averageAnnualRentTo = averageAnnualRentTo;
    }

    public String getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectIntro> getProjectIntro() {
        return projectIntro;
    }

    public void setProjectIntro(List<ProjectIntro> projectIntro) {
        this.projectIntro = projectIntro;
    }

    public List<ProjectGallery> getProjectGalleries() {
        return projectGalleries;
    }

    public void setProjectGalleries(List<ProjectGallery> projectGalleries) {
        this.projectGalleries = projectGalleries;
    }

    @Override
    public String toString() {
        return "ProjectDetail{" +
                "title='" + title + '\'' +
                ", country='" + country + '\'' +
                ", addressOne='" + addressOne + '\'' +
                ", addressTwo='" + addressTwo + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", averageAnnualRentFrom=" + averageAnnualRentFrom +
                ", averageAnnualRentTo=" + averageAnnualRentTo +
                ", downPayment='" + downPayment + '\'' +
                ", projectType='" + projectType + '\'' +
                ", description='" + description + '\'' +
                ", projectIntro=" + projectIntro +
                ", projectGalleries=" + projectGalleries +
                '}';
    }
}
