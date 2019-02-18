package com.century21.repository.api_project_details;

public class PropertyType {
    private int id;
    private String type;
    private int bedroom;
    private int floor;
    private Double width;
    private Double height;
    private int bathroom;
    private int parking;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBedroom() {
        return bedroom;
    }

    public void setBedroom(int bedroom) {
        this.bedroom = bedroom;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
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

    public int getBathroom() {
        return bathroom;
    }

    public void setBathroom(int bathroom) {
        this.bathroom = bathroom;
    }

    public int getParking() {
        return parking;
    }

    public void setParking(int parking) {
        this.parking = parking;
    }

    @Override
    public String toString() {
        return "PropertyType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", bedroom=" + bedroom +
                ", floor=" + floor +
                ", width=" + width +
                ", height=" + height +
                ", bathroom=" + bathroom +
                ", parking=" + parking +
                '}';
    }
}
