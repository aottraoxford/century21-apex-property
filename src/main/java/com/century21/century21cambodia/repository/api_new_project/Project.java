package com.century21.century21cambodia.repository.api_new_project;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class Project {
    private String name;
    @JsonProperty("built_date")
    private Date builtDate;
    @JsonProperty("completed_date")
    private Date completedDate;
    @JsonProperty("project_type_id")
    private Integer projectTypeID;
    private Double grr;
    @JsonProperty("down_payment")
    private String downPayment;
    private String description;
    @JsonProperty("introductions")
    private List<ProjectIntroduction> projectIntroductions;
    @JsonProperty("country_id")
    private Integer countryID;
    @JsonProperty("address_1")
    private String addressOne;
    @JsonProperty("address_2")
    private String addressTwo;
    private String status;
    private String city;
    @JsonProperty("min_price")
    private Double minPrice;
    @JsonProperty("max_price")
    private Double maxPrice;
    @JsonProperty("property_types")
    private List<PropertyType> propertyTypes;
    @JsonProperty("avg_rent_from")
    private Double avgRentFrom;
    @JsonProperty("avg_rent_to")
    private Double avgRentTo;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getAvgRentFrom() {
        return avgRentFrom;
    }

    public void setAvgRentFrom(Double avgRentFrom) {
        this.avgRentFrom = avgRentFrom;
    }

    public Double getAvgRentTo() {
        return avgRentTo;
    }

    public void setAvgRentTo(Double avgRentTo) {
        this.avgRentTo = avgRentTo;
    }

    public Integer getProjectTypeID() {
        return projectTypeID;
    }

    public void setProjectTypeID(Integer projectTypeID) {
        this.projectTypeID = projectTypeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public Double getGrr() {
        return grr;
    }

    public void setGrr(Double grr) {
        this.grr = grr;
    }

    public String getDownPayment() {
        return downPayment;
    }

    public void setDownPayment(String downPayment) {
        this.downPayment = downPayment;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ProjectIntroduction> getProjectIntroductions() {
        return projectIntroductions;
    }

    public void setProjectIntroductions(List<ProjectIntroduction> projectIntroductions) {
        this.projectIntroductions = projectIntroductions;
    }

    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String state) {
        this.status = status;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<PropertyType> getPropertyTypes() {
        return propertyTypes;
    }

    public void setPropertyTypes(List<PropertyType> propertyTypes) {
        this.propertyTypes = propertyTypes;
    }

    @Override
    public String toString() {
        return "Project{" +
                "name='" + name + '\'' +
                ", builtDate=" + builtDate +
                ", completedDate=" + completedDate +
                ", projectTypeID=" + projectTypeID +
                ", grr=" + grr +
                ", downPayment='" + downPayment + '\'' +
                ", description='" + description + '\'' +
                ", projectIntroductions=" + projectIntroductions +
                ", countryID=" + countryID +
                ", addressOne='" + addressOne + '\'' +
                ", addressTwo='" + addressTwo + '\'' +
                ", status='" + status + '\'' +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", propertyTypes=" + propertyTypes +
                ", avgRentFrom=" + avgRentFrom +
                ", avgRentTo=" + avgRentTo +
                '}';
    }
}
