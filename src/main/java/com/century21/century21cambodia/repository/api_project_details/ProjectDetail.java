package com.century21.century21cambodia.repository.api_project_details;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.List;

public class ProjectDetail {
    private int id;
    private String title;
    private String country;
    private String city;
    @JsonProperty("address_1")
    private String addressOne;
    @JsonProperty("address_2")
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
    private Double grr;
    @JsonProperty("introductions")
    private List<ProjectIntro> projectIntro;
    @JsonProperty("galleries")
    private List<ProjectGallery> projectGalleries;
    @JsonProperty("property_type")
    private List<PropertyType> propertyTypes;
    private boolean favorite;
    @JsonProperty("built_date")
    private Date builtDate;
    @JsonProperty("completed_date")
    private Date completedDate;

    public Date getBuiltDate() {
        return builtDate;
    }

    public void setBuiltDate(Date builtDate) {
        this.builtDate = builtDate;
    }

    public Date getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(Date completedDate) {
        this.completedDate = completedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<PropertyType> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(List<PropertyType> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    public String getTitle() {
        return title;
    }

    public Double getGrr() {
        return grr;
    }

    public void setGrr(Double grr) {
        this.grr = grr;
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
                ", city='" + city + '\'' +
                ", addressOne='" + addressOne + '\'' +
                ", addressTwo='" + addressTwo + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", averageAnnualRentFrom=" + averageAnnualRentFrom +
                ", averageAnnualRentTo=" + averageAnnualRentTo +
                ", downPayment='" + downPayment + '\'' +
                ", projectType='" + projectType + '\'' +
                ", description='" + description + '\'' +
                ", grr=" + grr +
                ", projectIntro=" + projectIntro +
                ", projectGalleries=" + projectGalleries +
                ", propertyTypes=" + propertyTypes +
                ", favorite=" + favorite +
                '}';
    }
}
