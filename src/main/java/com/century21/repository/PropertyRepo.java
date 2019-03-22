package com.century21.repository;

import com.century21.model.ID;
import com.century21.util.Url;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepo {

    @Insert("INSERT INTO property_neighborhood(property_id,address,distance) " +
            "VALUES(#{nbh.propertyID},#{nbh.address},#{nbh.distance})")
    int insertNeighborhood(@Param("nbh")Neighborhood neighborhood);

    @Select("SELECT lat,lng,id,project_id,user_id,title,unit_price,sqm_price,country,type,status " +
            "FROM property " +
            "WHERE user_id=#{userID}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "unitPrice",column = "unit_price"),
            @Result(property = "sqmPrice",column = "sqm_price"),
            @Result(property = "gallery",column = "id",many = @Many(select = "gallery")),
            @Result(property = "user",column = "user_id",one = @One(select = "findOneUser"))
    })
    List<Properties> findAgentProperties(int userID);

    @Update("UPDATE property SET status = #{status} " +
            "WHERE id = #{proID}")
    int updateStatus(@Param("proID")int projectID,@Param("status") boolean status);

    @SelectProvider(type = PropertyUtil.class,method = "findAllPropertyByFilter")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "unitPrice",column = "unit_price"),
            @Result(property = "sqmPrice",column = "sqm_price"),
            @Result(property = "gallery",column = "id",many = @Many(select = "gallery")),
            @Result(property = "user",column = "user_id",one = @One(select = "findOneUser"))
    })
    List<Properties> findAllPropertyByFilter(@Param("filter")PropertyFilter filter,@Param("limit")int limit,@Param("offset")int offset);

    @SelectProvider(type = PropertyUtil.class,method = "findAllPropertyByFilterCount")
    int findAllPropertyByFilterCount(@Param("filter")PropertyFilter filter);

    @Delete("DELETE FROM property_file WHERE property_id=#{proID} AND name = #{name}")
    void removeFile(@Param("proID")int proID,@Param("name")String name);

    @Select("SELECT id,name " +
            "FROM property_files " +
            "WHERE type = 'image' AND property_id=#{id}")
    @Results({
            @Result(property = "gallery",column = "name")
    })
    List<Gallery> gallery();

    @SelectProvider(type = PropertyUtil.class,method = "findAllPropertyCount")
    int findAllPropertyCount();

    @SelectProvider(type = PropertyUtil.class,method = "findAllProperty")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "unitPrice",column = "unit_price"),
            @Result(property = "sqmPrice",column = "sqm_price"),
            @Result(property = "rentOrBuy",column = "rent_or_sell"),
            @Result(property = "galleries",column = "id",many = @Many(select = "findGalleries")),
            @Result(property = "user",column = "user_id",one = @One(select = "findOneUser"))
    })
    List<Properties> findAllProperty(@Param("limit")int limit,@Param("offset")int offset);

    @Select("SELECT id,name " +
            "FROM property_files " +
            "WHERE type = 'image' AND property_id=#{id}")
    @Results({
            @Result(property = "gallery",column = "name")
    })
    List<Gallery> findGalleries();

    @Select("SELECT * FROM property_files " +
            "WHERE property_id=#{id} AND type = 'doc'")
    List<PropertyFile> findDocs();

    @SelectProvider(type = PropertyUtil.class,method = "findOneProperty")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "projectID",column = "project_id"),
            @Result(property = "livingRoom",column = "living_room"),
            @Result(property = "dinningRoom",column = "dinning_room"),
            @Result(property = "airConditioner",column = "air_conditioner"),
            @Result(property = "mezzanineFloor",column = "mezzanine_floor"),
            @Result(property = "rentOrSell",column = "rent_or_sell"),
            @Result(property = "houseNo",column = "house_no"),
            @Result(property = "streetNo",column = "street_no"),
            @Result(property = "privateArea",column = "private_area"),
            @Result(property = "commonArea",column = "common_area"),
            @Result(property = "unitPrice",column = "unit_price"),
            @Result(property = "sqmPrice",column = "sqm_price"),
            @Result(property = "totalLandArea",column = "total_land_area"),
            @Result(property = "landWidth",column = "land_width"),
            @Result(property = "landLength",column = "land_length"),
            @Result(property = "totalArea",column = "total_area"),
            @Result(property = "showMap",column = "show_map"),
            @Result(property = "galleries",column = "id",many = @Many(select = "findGalleries")),
            @Result(property = "docs",column = "id",many = @Many(select = "findDocs")),
            @Result(property = "user",column = "user_id",one = @One(select = "findOneUser")),
            @Result(property = "neighborhoods",column = "id",many = @Many(select = "findNeighborhoods"))
    })
    Property findOneProperty(@Param("proID")int proID);

    @Select("SELECT * " +
            "FROM property_neighborhood " +
            "WHERE property_id=#{id}")
    @Results({
            @Result(property = "propertyID",column = "property_id")
    })
    List<Neighborhood> findNeighborhoods();

    @Select("SELECT id,first_name,last_name,email,gender,phone_number,image,account_type " +
            "FROM users " +
            "WHERE id=#{user_id}")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "firstName",column = "first_name"),
            @Result(property = "lastName",column = "last_name"),
            @Result(property = "phoneNumber",column = "phone_number"),
            @Result(property = "accountType",column = "account_type")
    })
    UserRepo.User findOneUser();

    @Insert("INSERT into property_files(name,type,property_id) " +
            "VALUES(#{name},#{type},#{proID}) ")
    Integer insertFiles(@Param("name")String name,@Param("type")String type,@Param("proID")int proID);

    @InsertProvider(type = PropertyUtil.class,method = "insertProperty")
    @SelectKey(statement = "select nextval('property_id_seq') ", resultType = int.class, before = true, keyProperty = "id.id")
    Integer insertProperty(@Param("id")ID id,@Param("ppt")PropertyRequest propertyRequest,@Param("userID")int userID);

    class PropertyUtil{
        public String findAllPropertyByFilter(@Param("filter")PropertyFilter filter,@Param("limit")int limit,@Param("offset")int offset){
            return new SQL(){
                {
                    SELECT("lat,lng,property.id,property.project_id,property.user_id,property.title,property.unit_price,property.sqm_price,property.country,property.type,property.status");
                    FROM("property");
                    LEFT_OUTER_JOIN("project ON project.id=property.project_id");
                    if(filter.getTitle()!=null && filter.getTitle().length()>0) WHERE("property.title ILIKE '%'||#{filter.title}||'%'");
                    if(filter.getRentOrBuy()!=null && filter.getRentOrBuy().length()>0) WHERE("property.rent_or_sell ilike #{filter.rentOrBuy}");
                    if(filter.getCity()!=null && filter.getCity().length()>0) WHERE("property.city ilike #{filter.city}");
                    if(filter.getDistrict()!=null && filter.getDistrict().length()>0) WHERE("property.district ilike #{filter.district}");
                    if(filter.getCommune()!=null && filter.getCommune().length()>0) WHERE("property.commune ilike #{filter.commune}");
                    if(filter.getType()!=null && filter.getType().length()>0) WHERE("property.type ilike #{filter.type}");
                    if(filter.getStatus()!=null && filter.getStatus().length()>0) {
                        if(filter.getStatus().equalsIgnoreCase("true")) WHERE("property.status IS TRUE");
                        else WHERE("property.status IS FALSE");
                    }
                    if(filter.getBedroom()>0) WHERE("property.bedroom = #{filter.bedroom}");
                    if(filter.getBathroom()>0) WHERE("property.bathroom = #{filter.bathroom}");
                    if(filter.getToPrice()>0 && filter.getFromPrice()>0) WHERE("property.price between #{filter.fromPrice} AND #{filter.toPrice}");
                    else if(filter.getFromPrice()>0) WHERE("property.price > #{filter.fromPrice}");
                    else if(filter.getToPrice()>0) WHERE("property.price < #{filter.toPrice}");
                    if(filter.getSortType()!=null && filter.getSortType().length()>0 ){
                        if(filter.getSortType().equalsIgnoreCase("price"))
                            ORDER_BY("price limit #{limit} offset #{offset}");
                        else if(filter.getSortType().equalsIgnoreCase("price-desc"))
                            ORDER_BY("price DESC limit #{limit} offset #{offset}");
                        else if(filter.getSortType().equalsIgnoreCase("title"))
                            ORDER_BY("name limit #{limit} offset #{offset}");
                        else if(filter.getSortType().equalsIgnoreCase("title-desc"))
                            ORDER_BY("name DESC limit #{limit} offset #{offset}");
                    }else ORDER_BY("property.id DESC limit #{limit} offset #{offset}");
                }
            }.toString();
        }

        public String findAllPropertyByFilterCount(@Param("filter")PropertyFilter filter){
            return new SQL(){
                {
                    SELECT("count(property.id)");
                    FROM("property");
                    LEFT_OUTER_JOIN("project ON project.id=property.project_id");
                    if(filter.getTitle()!=null && filter.getTitle().length()>0) WHERE("property.title ILIKE '%'||#{filter.title}||'%'");
                    if(filter.getRentOrBuy()!=null && filter.getRentOrBuy().length()>0) WHERE("property.rent_or_sell ilike #{filter.rentOrBuy}");
                    if(filter.getCity()!=null && filter.getCity().length()>0) WHERE("property.city ilike #{filter.city}");
                    if(filter.getDistrict()!=null && filter.getDistrict().length()>0) WHERE("property.district ilike #{filter.district}");
                    if(filter.getCommune()!=null && filter.getCommune().length()>0) WHERE("property.commune ilike #{filter.commune}");
                    if(filter.getType()!=null && filter.getType().length()>0) WHERE("property.type ilike #{filter.type}");
                    if(filter.getStatus()!=null && filter.getStatus().length()>0) {
                        if(filter.getStatus().equalsIgnoreCase("true")) WHERE("property.status IS TRUE");
                        else WHERE("property.status IS FALSE");
                    }
                    if(filter.getBedroom()>0) WHERE("property.bedroom = #{filter.bedroom}");
                    if(filter.getBathroom()>0) WHERE("property.bathroom = #{filter.bathroom}");
                    if(filter.getToPrice()>0) WHERE("property.price between #{filter.fromPrice} AND #{filter.toPrice}");
                }
            }.toString();
        }

        public String findAllPropertyCount(){
            return new SQL(){
                {
                    SELECT("count(id)");
                    FROM("property");
                    WHERE("status IS TRUE");
                }
            }.toString();
        }

        public String findAllProperty(@Param("limit")int limit,@Param("offset")int offset){
            return new SQL(){
                {
                    SELECT("lat,lng,id,user_id,title,unit_price,sqm_price,country,type,status,rent_or_sell");
                    FROM("property");
                    WHERE("status IS TRUE");
                    ORDER_BY("id DESC limit #{limit} offset #{offset}");
                }
            }.toString();
        }

        public String findOneProperty(@Param("proID")int proID){
            return new SQL(){
                {
                    SELECT("*");
                    FROM("property");
                    WHERE("id=#{proID}");
                }
            }.toString();
        }
        public String insertProperty(@Param("id") ID id, @Param("ppt")PropertyRequest propertyRequest,@Param("userID")int userID){
            return new SQL(){
                {
                    INSERT_INTO("property");
                    VALUES("user_id,id,project_id,bedroom,bathroom,living_room,dinning_room,kitchen,air_conditioner,parking,balcony,mezzanine_floor,title,rent_or_sell,type,city,district,commune,village,house_no,street_no,description,private_area,common_area,unit_price,sqm_price,lat,lng,width,height,total_area,land_width,land_length,total_land_area,status,show_map",
                            "#{userID},#{id.id},#{ppt.projectID},#{ppt.bedroom},#{ppt.bathroom},#{ppt.livingRoom},#{ppt.dinningRoom},#{ppt.kitchen},#{ppt.airConditioner},#{ppt.parking},#{ppt.balcony},#{ppt.mezzanineFloor},#{ppt.title},#{ppt.rentOrSell},#{ppt.type},#{ppt.city},#{ppt.district},#{ppt.commune},#{ppt.village},#{ppt.houseNo},#{ppt.streetNo},#{ppt.description},#{ppt.privateArea},#{ppt.commonArea},#{ppt.unitPrice},#{ppt.sqmPrice},#{ppt.lat},#{ppt.lng},#{ppt.width},#{ppt.height},#{ppt.totalArea},#{ppt.landWidth},#{ppt.landLength},#{ppt.totalLandArea},#{ppt.status},#{ppt.showMap}");
                }
            }.toString();
        }
    }

    class PropertyFilter{
        private String title;
        @JsonProperty("rent_or_buy")
        private String rentOrBuy;
        @JsonProperty("sort_type")
        private String sortType;
        private String city;
        private String type;
        private String district;
        private String commune;
        private String status;
        private int bedroom;
        private int bathroom;
        @JsonProperty("from_price")
        private double fromPrice;
        @JsonProperty("to_price")
        private double toPrice;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getRentOrBuy() {
            return rentOrBuy;
        }

        public void setRentOrBuy(String rentOrBuy) {
            this.rentOrBuy = rentOrBuy;
        }

        public String getSortType() {
            return sortType;
        }

        public void setSortType(String sortType) {
            this.sortType = sortType;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
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

        public double getFromPrice() {
            return fromPrice;
        }

        public void setFromPrice(double fromPrice) {
            this.fromPrice = fromPrice;
        }

        public double getToPrice() {
            return toPrice;
        }

        public void setToPrice(double toPrice) {
            this.toPrice = toPrice;
        }
    }

    class Gallery{
        private int id;
        @JsonProperty("url")
        private String gallery;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getGallery() {
            if(gallery!=null) return Url.propertyGalleryUrl+gallery;
            return gallery;
        }

        public void setGallery(String gallery) {
            this.gallery = gallery;
        }
    }

    class Properties{
       private int id;
       private String title;
       private String country;
       private String type;
       @JsonProperty("rent_or_buy")
       private String rentOrBuy;
       @JsonProperty("unit_price")
       private double unitPrice;
       @JsonProperty("sqm_price")
       private double sqmPrice;
       private double lat;
       private double lng;
       private boolean status;
       private UserRepo.User user;
       private List<Gallery> galleries;

        public String getRentOrBuy() {
            return rentOrBuy;
        }

        public void setRentOrBuy(String rentOrBuy) {
            this.rentOrBuy = rentOrBuy;
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

        public UserRepo.User getUser() {
            return user;
        }

        public void setUser(UserRepo.User user) {
            this.user = user;
        }

        public List<Gallery> getGalleries() {
            return galleries;
        }

        public void setGalleries(List<Gallery> galleries) {
            this.galleries = galleries;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }
    }
    class Property{
        private int id;
        @JsonProperty("project_id")
        private Integer projectID;
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
        @JsonProperty("private_area")
        private double privateArea;
        @JsonProperty("common_area")
        private double commonArea;
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
        private boolean isFavorite;
        private UserRepo.User user;
        List<Gallery> galleries;
        List<PropertyFile> docs;
        List<Neighborhood> neighborhoods;

        public List<Neighborhood> getNeighborhoods() {
            return neighborhoods;
        }

        public void setNeighborhoods(List<Neighborhood> neighborhoods) {
            this.neighborhoods = neighborhoods;
        }

        public UserRepo.User getUser() {
            return user;
        }

        public void setUser(UserRepo.User user) {
            this.user = user;
        }

        public boolean isFavorite() {
            return isFavorite;
        }

        public void setFavorite(boolean favorite) {
            isFavorite = favorite;
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

        public double getPrivateArea() {
            return privateArea;
        }

        public void setPrivateArea(double privateArea) {
            this.privateArea = privateArea;
        }

        public double getCommonArea() {
            return commonArea;
        }

        public void setCommonArea(double commonArea) {
            this.commonArea = commonArea;
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

        public double getTotalLandArea() {
            return totalLandArea;
        }

        public void setTotalLandArea(double totalLandArea) {
            this.totalLandArea = totalLandArea;
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

        public List<Gallery> getGalleries() {
            return galleries;
        }

        public void setGalleries(List<Gallery> galleries) {
            this.galleries = galleries;
        }

        public List<PropertyFile> getDocs() {
            return docs;
        }

        public void setDocs(List<PropertyFile> docs) {
            this.docs = docs;
        }
    }
    class PropertyFile{
        private int id;
        private String name;
        private String type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            if(name!=null)
                if(type.equals("image")) return Url.propertyGalleryUrl+name;
                else if(type.equals("doc")) return Url.propertyDocsUrl+name;
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
    class Neighborhood{
        private int id;
        @JsonProperty("property_id")
        private int propertyID;
        private String address;
        private double distance;

        public int getPropertyID() {
            return propertyID;
        }

        public void setPropertyID(int propertyID) {
            this.propertyID = propertyID;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }
    }
    class PropertyRequest{
        private int id;
        @JsonProperty("project_id")
        private Integer projectID;
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
        @JsonProperty("private_area")
        private double privateArea;
        @JsonProperty("common_area")
        private double commonArea;
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
        private List<Neighborhood> neighborhood;

        public List<Neighborhood> getNeighborhood() {
            return neighborhood;
        }

        public void setNeighborhood(List<Neighborhood> neighborhood) {
            this.neighborhood = neighborhood;
        }

        public double getPrivateArea() {
            return privateArea;
        }

        public void setPrivateArea(double privateArea) {
            this.privateArea = privateArea;
        }

        public double getCommonArea() {
            return commonArea;
        }

        public void setCommonArea(double commonArea) {
            this.commonArea = commonArea;
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

        public Integer getProjectID() {
            if(projectID==0) return null;
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
