package com.century21.repository.api_update_project;

import com.century21.repository.api_project_details.ProjectIntro;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UpdateProj {
    @NotNull
    @JsonProperty("project_id")
    private int projectID;
    @JsonProperty("built_date")
    private String builtDate;
    @JsonProperty("completed_date")
    private String completedDate;
    private String title;
    private String country;
    @JsonProperty("address_1")
    private String addressOne;
    @JsonProperty("address_2")
    private String addressTwo;
    @NotNull
    @JsonProperty("start_price")
    private double minPrice;
    @NotNull
    @JsonProperty("end_price")
    private double maxPrice;
    @NotNull
    @JsonProperty("avg_annual_rent_from")
    private double averageAnnualRentFrom;
    @NotNull
    @JsonProperty("avg_annual_rent_to")
    private double averageAnnualRentTo;
    @JsonProperty("down_payment")
    private String downPayment;
    @JsonProperty("project_type")
    private String projectType;
    private String description;
    private String status;
    private double grr;
    private String city;
    @JsonProperty("introductions")
    private List<ProjectIntro> projectIntro;

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getBuiltDate() {
        return builtDate;
    }

    public void setBuiltDate(String builtDate) {
        this.builtDate = builtDate;
    }

    public String getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getGrr() {
        return grr;
    }

    public void setGrr(double grr) {
        this.grr = grr;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<ProjectIntro> getProjectIntro() {
        return projectIntro;
    }

    public void setProjectIntro(List<ProjectIntro> projectIntro) {
        this.projectIntro = projectIntro;
    }

    @Override
    public String toString() {
        return "UpdateProj{" +
                "projectID=" + projectID +
                ", builtDate='" + builtDate + '\'' +
                ", completedDate='" + completedDate + '\'' +
                ", title='" + title + '\'' +
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
                ", status='" + status + '\'' +
                ", grr=" + grr +
                ", city='" + city + '\'' +
                ", projectIntro=" + projectIntro +
                '}';
    }
}