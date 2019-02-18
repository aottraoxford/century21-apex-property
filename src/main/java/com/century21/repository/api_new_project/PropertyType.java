package com.century21.repository.api_new_project;

public class PropertyType {
    private String type;
    private Integer floor;
    private Double width;
    private Double height;
    private Integer bedroom;
    private Integer bathroom;
    private Integer parking;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Integer getBedroom() {
        return bedroom;
    }

    public void setBedroom(Integer bedroom) {
        this.bedroom = bedroom;
    }

    public Integer getBathroom() {
        return bathroom;
    }

    public void setBathroom(Integer bathroom) {
        this.bathroom = bathroom;
    }

    public Integer getParking() {
        return parking;
    }

    public void setParking(Integer parking) {
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "PropertyType{" +
                "type='" + type + '\'' +
                ", floor=" + floor +
                ", width=" + width +
                ", height=" + height +
                ", bedroom=" + bedroom +
                ", bathroom=" + bathroom +
                ", parking=" + parking +
                '}';
    }
}
