package com.century21.repository.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SearchParam {
    private String title;
    @JsonProperty("rent_or_buy")
    private String rentOrBuy;
    private String sort;
    @JsonProperty("project_type_id")
    private int projectTypeID;
    @JsonProperty("country_id")
    private int countryID;
    private String city;
    @JsonProperty("room_amount")
    private int roomAmount;
    @JsonProperty("start_price")
    private double startPrice;
    @JsonProperty("end_price")
    private double endPrice;

    public String getTitle() {
        if(title!=null) {
            title = title.trim();
            if (title.length() < 1)
                return null;
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRentOrBuy() {
        if(rentOrBuy!=null) {
            rentOrBuy = rentOrBuy.trim();
            if (rentOrBuy.length() < 1)
                return null;
        }
        return rentOrBuy;
    }

    public void setRentOrBuy(String rentOrBuy) {
        this.rentOrBuy = rentOrBuy;
    }

    public String getSort() {
        if(sort!=null) {
            sort = sort.trim();
            if (sort.length() < 1)
                return null;
        }
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCity() {
        if(city!=null) {
            city = city.trim();
            if (city.length() < 1)
                return null;
        }
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getProjectTypeID() {
        return projectTypeID;
    }

    public void setProjectTypeID(int projectTypeID) {
        this.projectTypeID = projectTypeID;
    }

    public int getCountryID() {
        return countryID;
    }

    public void setCountryID(int countryID) {
        this.countryID = countryID;
    }

    public int getRoomAmount() {
        return roomAmount;
    }

    public void setRoomAmount(int roomAmount) {
        this.roomAmount = roomAmount;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

    @Override
    public String toString() {
        return "SearchParam{" +
                "title='" + title + '\'' +
                ", rentOrBuy='" + rentOrBuy + '\'' +
                ", sort='" + sort + '\'' +
                ", projectTypeID=" + projectTypeID +
                ", countryID=" + countryID +
                ", roomAmount=" + roomAmount +
                ", startPrice=" + startPrice +
                ", endPrice=" + endPrice +
                '}';
    }
}
