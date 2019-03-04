package com.century21.repository;

import com.century21.model.ID;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyRepo {
    @InsertProvider(type = PropertyUtil.class,method = "insertProperty")
    @SelectKey(statement = "select nextval('property_id_seq') ", resultType = int.class, before = true, keyProperty = "id.id")
    Integer insertProperty(@Param("id")ID id,@Param("ppt")PropertyRequest propertyRequest);

    class PropertyUtil{
        public String insertProperty(@Param("id") ID id, @Param("ppt")PropertyRequest propertyRequest){
            return new SQL(){
                {
                    INSERT_INTO("property");
                    VALUES("id,project_id,bedroom,bathroom,living_room,dinning_room,kitchen,air_conditioner,parking,balcony,mezzanine_floor,title,rent_or_sell,type,city,district,commune,village,house_no,street_no,description,unit_price,sqm_price,lat,lng,width,height,total_area,land_width,land_length,total_land_area,status,show_map",
                            "#{id.id},#{ppt.projectID},#{ppt.bedroom},#{ppt.bathroom},#{ppt.livingRoom},#{ppt.dinningRoom},#{ppt.kitchen},#{ppt.airConditioner},#{ppt.parking},#{ppt.balcony},#{ppt.mezzanineFloor},#{ppt.title},#{ppt.rentOrSell},#{ppt.type},#{ppt.city},#{ppt.district},#{ppt.commune},#{ppt.village},#{ppt.houseNo},#{ppt.streetNo},#{ppt.description},#{ppt.unitPrice},#{ppt.sqmPrice},#{ppt.lat},#{ppt.lng},#{ppt.width},#{ppt.height},#{ppt.totalArea},#{ppt.landWidth},#{ppt.landLength},#{ppt.totalLandArea},#{ppt.status},#{ppt.showMap}");
                }
            }.toString();
        }
    }
    class PropertyResponse{
        private int id;
        @JsonProperty("project_id")
        private int projectID;
        private String title;
        @JsonProperty("rent_or_sell")
        private String rentOrSell;
        private String type;
        private String city;
        private String district;
        private String commune;
        private String village;
        @JsonProperty("house_no")
        private String houseNo;
        @JsonProperty("street_no")
        private String streetNo;
        @JsonProperty("unit_price")
        private double unitPrice;
        @JsonProperty("sqm_price")
        private double sqmPrice;
        private double lat;
        private double lng;
        private double width;
        private double height;
        @JsonProperty("land_width")
        private double landWidth;
        @JsonProperty("land_length")
        private double landLength;
        @JsonProperty("total_land_area")
        private double totalLandArea;
        @JsonProperty("total_area")
        private double totalArea;
        private boolean status;
        @JsonProperty("show_map")
        private boolean showMap;

        public double getLandWidth() {
            return landWidth;
        }

        public void setLandWidth(double landWidth) {
            this.landWidth = landWidth;
        }

        public double getLandLength() {
            return landLength;
        }

        public void setLandLength(double landLength) {
            this.landLength = landLength;
        }

        public double getTotalLandArea() {
            return totalLandArea;
        }

        public void setTotalLandArea(double totalLandArea) {
            this.totalLandArea = totalLandArea;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProjectID() {
            return projectID;
        }

        public void setProjectID(int projectID) {
            this.projectID = projectID;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRentOrSell() {
            return rentOrSell;
        }

        public void setRentOrSell(String rentOrSell) {
            this.rentOrSell = rentOrSell;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCommune() {
            return commune;
        }

        public void setCommune(String commune) {
            this.commune = commune;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }

        public String getStreetNo() {
            return streetNo;
        }

        public void setStreetNo(String streetNo) {
            this.streetNo = streetNo;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public double getSqmPrice() {
            return sqmPrice;
        }

        public void setSqmPrice(double sqmPrice) {
            this.sqmPrice = sqmPrice;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public double getTotalArea() {
            return totalArea;
        }

        public void setTotalArea(double totalArea) {
            this.totalArea = totalArea;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public boolean isShowMap() {
            return showMap;
        }

        public void setShowMap(boolean showMap) {
            this.showMap = showMap;
        }
    }
    class PropertyRequest{
        private int id;
        @JsonProperty("project_id")
        private Integer projectID=null;
        private int bedroom;
        private int bathroom;
        @JsonProperty("living_room")
        private int livingRoom;
        @JsonProperty("dinning_room")
        private int dinningRoom;
        private int kitchen;
        @JsonProperty("air_conditioner")
        private int airConditioner;
        private int parking;
        private int balcony;
        @JsonProperty("mezzanine_floor")
        private int mezzanineFloor;
        private String title;
        @JsonProperty("rent_or_sell")
        private String rentOrSell;
        private String type;
        private String city;
        private String district;
        private String commune;
        private String village;
        @JsonProperty("house_no")
        private String houseNo;
        @JsonProperty("street_no")
        private String streetNo;
        private String description;
        @JsonProperty("unit_price")
        private double unitPrice;
        @JsonProperty("sqm_price")
        private double sqmPrice;
        private double lat;
        private double lng;
        @JsonProperty("total_land_area")
        private double totalLandArea;
        @JsonProperty("building_width")
        private double width;
        @JsonProperty("building_height")
        private double height;
        @JsonProperty("land_width")
        private double landWidth;
        @JsonProperty("land_length")
        private double landLength;
        @JsonProperty("total_area")
        private double totalArea;
        private boolean status;
        @JsonProperty("show_map")
        private boolean showMap;

        public double getTotalLandArea() {
            return totalLandArea;
        }

        public void setTotalLandArea(double totalLandArea) {
            this.totalLandArea = totalLandArea;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Integer getProjectID() {
            return projectID;
        }

        public void setProjectID(Integer projectID) {
            this.projectID = projectID;
        }

        public int getBedroom() {
            return bedroom;
        }

        public void setBedroom(int bedroom) {
            this.bedroom = bedroom;
        }

        public int getBathroom() {
            return bathroom;
        }

        public void setBathroom(int bathroom) {
            this.bathroom = bathroom;
        }

        public int getLivingRoom() {
            return livingRoom;
        }

        public void setLivingRoom(int livingRoom) {
            this.livingRoom = livingRoom;
        }

        public int getDinningRoom() {
            return dinningRoom;
        }

        public void setDinningRoom(int dinningRoom) {
            this.dinningRoom = dinningRoom;
        }

        public int getKitchen() {
            return kitchen;
        }

        public void setKitchen(int kitchen) {
            this.kitchen = kitchen;
        }

        public int getAirConditioner() {
            return airConditioner;
        }

        public void setAirConditioner(int airConditioner) {
            this.airConditioner = airConditioner;
        }

        public int getParking() {
            return parking;
        }

        public void setParking(int parking) {
            this.parking = parking;
        }

        public int getBalcony() {
            return balcony;
        }

        public void setBalcony(int balcony) {
            this.balcony = balcony;
        }

        public int getMezzanineFloor() {
            return mezzanineFloor;
        }

        public void setMezzanineFloor(int mezzanineFloor) {
            this.mezzanineFloor = mezzanineFloor;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRentOrSell() {
            return rentOrSell;
        }

        public void setRentOrSell(String rentOrSell) {
            this.rentOrSell = rentOrSell;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getCommune() {
            return commune;
        }

        public void setCommune(String commune) {
            this.commune = commune;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }

        public String getStreetNo() {
            return streetNo;
        }

        public void setStreetNo(String streetNo) {
            this.streetNo = streetNo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(double unitPrice) {
            this.unitPrice = unitPrice;
        }

        public double getSqmPrice() {
            return sqmPrice;
        }

        public void setSqmPrice(double sqmPrice) {
            this.sqmPrice = sqmPrice;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public double getLandWidth() {
            return landWidth;
        }

        public void setLandWidth(double landWidth) {
            this.landWidth = landWidth;
        }

        public double getLandLength() {
            return landLength;
        }

        public void setLandLength(double landLength) {
            this.landLength = landLength;
        }

        public double getTotalArea() {
            return totalArea;
        }

        public void setTotalArea(double totalArea) {
            this.totalArea = totalArea;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public boolean isShowMap() {
            return showMap;
        }

        public void setShowMap(boolean showMap) {
            this.showMap = showMap;
        }
    }
}
