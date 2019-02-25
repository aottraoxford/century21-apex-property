package com.century21.repository;

import com.century21.model.ID;
import com.century21.model.Pagination;
import com.century21.util.Url;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Repository
public interface ProjectRepo {


    @Select("SELECT id " +
            "FROM project_intro " +
            "WHERE project_id=#{proID}")
    List<Integer> findAllProjectIntroID(int proID);

    @Update("UPDATE project_intro " +
            "SET name=#{pi.name},description=#{pi.description} " +
            "WHERE project_id=#{proID} AND id=#{pi.id}")
    Integer updateProjectIntro(@Param("pi")ProjectIntroduction projectIntroduction,@Param("proID")int proID);

    @Delete("DELETE FROM project_intro " +
            "WHERE id=#{id} AND project_id=#{proID}")
    Integer removeProjectIntro(@Param("id")int id,@Param("proID")int projectID);

    @Select("SELECT id " +
            "FROM country " +
            "WHERE name ilike #{name}")
    Integer findCountryIDByName(String name);

    @Select("SELECT id " +
            "FROM project_type " +
            "WHERE name ilike #{name}")
    Integer findProjectTypeIDByName(String name);

    @Select("SELECT DISTINCT(country.name),country.id " +
        "FROM country " +
        "INNER JOIN project ON project.country_id=country.id " +
        "WHERE project.isdisplay IS TRUE " +
        "ORDER BY country.id ")
    @Results({
            @Result(property = "projectTypeForWebList",column = "id",many = @Many(select = "getProjectTypeForWeb")),
            @Result(property = "countryName",column = "name"),
            @Result(property = "countryID",column = "id")
    })
    List<CountryForWeb> getCountryForWeb();

    @Select("SELECT DISTINCT(project_type.name),project_type.id ,project.country_id " +
            "FROM project_type " +
            "INNER JOIN project ON project.project_type_id=project_type.id " +
            "WHERE project.country_id = #{id} " +
            "ORDER BY project_type.id")
    @Results({
            //@Result(property = "projectList",column = "{cid=country_id,pid=id}",many = @Many(select = "getProjectForWeb")),
            @Result(property = "type",column = "name")
    })
    List<ProjectTypeForWeb> getProjectTypeForWeb();

    @Select("SELECT id,name,start_price,end_price,grr,country_id,project_type_id,thumbnail " +
            "FROM project " +
            "WHERE country_id=#{cid} AND project_type_id=#{pid} " +
            "ORDER BY id DESC LIMIT 1")
    List<Project> getProjectForWeb();

    @Select("SELECT id " +
            "FROM favorite " +
            "WHERE project_id=#{projectID} AND user_id=#{userID}")
    Integer favorite(@Param("projectID") int projectID, @Param("userID") int userID);

    @Delete("DELETE FROM project_gallery " +
            "WHERE url=#{name} AND type='image' ")
    void removeOneGallery(@Param("name")String name);

    @SelectProvider(type = ProjectUtil.class,method = "findAllProject")
    @Results({
            @Result(property = "status",column = "isdisplay"),
            @Result(property = "startPrice",column = "start_price"),
            @Result(property = "endPrice",column = "end_price"),
            @Result(property = "country",column = "country_id",one = @One(select = "country")),
            @Result(property = "projectType",column = "project_type_id",one = @One(select = "projectType"))
    })
    List<ProjectListingReponse> findAllProject(@Param("title")String title,@Param("cid") int cid, @Param("pid") int pid, @Param("status") String status, @Param("limit") int limit,@Param("offset")int offset);

    @SelectProvider(type = ProjectUtil.class,method = "findAllProjectCount")
    int findAllProjectCount(@Param("title")String title,@Param("cid") int cid, @Param("pid") int pid, @Param("status") String status);

    @Select("SELECT thumbnail " +
            "FROM project " +
            "WHERE id=#{proID}")
    String findOneThumbnail(@Param("proID")int projectID);

    @InsertProvider(type = ProjectUtil.class, method = "insertGallery")
    void insertGallery(@Param("gall") String galleries, @Param("proID") int projectID);

    @UpdateProvider(type = ProjectUtil.class, method = "updateThumbnail")
    void updateThumbnail(@Param("thumbnail") String thumbnail, @Param("proID") int projectID);

    @SelectProvider(type = ProjectUtil.class, method = "findOneProject")
    @Results(value = {
            @Result(property = "id",column = "id"),
            @Result(property = "builtDate", column = "built_date"),
            @Result(property = "completedDate", column = "completed_date"),
            @Result(property = "title", column = "name"),
            @Result(property = "addressOne", column = "address_1"),
            @Result(property = "addressTwo", column = "address_2"),
            @Result(property = "minPrice", column = "start_price"),
            @Result(property = "maxPrice", column = "end_price"),
            @Result(property = "averageAnnualRentFrom", column = "avg_rent_from"),
            @Result(property = "averageAnnualRentTo", column = "avg_rent_to"),
            @Result(property = "downPayment", column = "down_payment"),
            @Result(property = "status",column = "rent_or_buy"),
            @Result(property = "country", column = "country_id", one = @One(select = "country")),
            @Result(property = "projectType", column = "project_type_id", one = @One(select = "projectType")),
            @Result(property = "projectIntro", column = "id", many = @Many(select = "projectIntro")),
            @Result(property = "projectGalleries", column = "id", many = @Many(select = "projectGalleries")),
            @Result(property = "propertyTypes", column = "id", many = @Many(select = "propertyTypes")),
            @Result(property = "towerTypes", column = "id", many = @Many(select = "towerTypes"))
    })
    Project findOneProject(@Param("proID") int projectID);

    @Select("SELECT name " +
            "FROM country " +
            "WHERE id=#{country_id}")
    String country();

    @Select("SELECT name " +
            "FROM project_type " +
            "WHERE id=#{project_type_id}")
    String projectType();

    @Select("SELECT id,name,description " +
            "FROM project_intro " +
            "WHERE project_id=#{id}")
    List<ProjectIntroduction> projectIntro();

    @Select("SELECT url " +
            "FROM project_gallery " +
            "WHERE project_id=#{id} AND type='image'")
    @Results({
            @Result(property = "image", column = "url")
    })
    List<ProjectGallery> projectGalleries();

    @Select("SELECT * " +
            "FROM property_type " +
            "WHERE project_id=#{id}")
    @Results({
            @Result(property = "type", column = "name")
    })
    List<PropertyType> propertyTypes();

    @Select("SELECT * " +
            "FROM tower_type " +
            "WHERE project_id=#{id}")
    List<TowerType> towerTypes();

    @UpdateProvider(type = ProjectUtil.class, method = "updateProject")
    void updateProject(@Param("pro")ProjectRequest projectRequest);

    @InsertProvider(type = ProjectUtil.class, method = "insertProject")
    @SelectKey(statement = "select nextval('project_id_seq') ", resultType = int.class, before = true, keyProperty = "id.id")
    int insertProject(@Param("id") ID id, @Param("project") ProjectRequest project);

    @InsertProvider(type = ProjectUtil.class, method = "insertProjectIntro")
    int insertProjectIntro(@Param("intro") ProjectIntroduction intro, @Param("proID") int projectID);

    @InsertProvider(type = ProjectUtil.class, method = "insertPropertyType")
    int insertPropertyType(@Param("pt") PropertyType propertyType, @Param("proID") int projectID);

    @SelectProvider(type = ProjectUtil.class, method = "insertTowerType")
    void insertTowerType(@Param("type")String type,@Param("proID") int projectID);

    @Select("SELECT id " +
            "FROM property_type " +
            "WHERE project_id=#{proID}")
    List<Integer> findAllPropertyTypeID(@Param("proID")int proID);

    @Update("UPDATE property_type " +
            "SET name=#{pt.type},bedroom=#{pt.bedroom},floor=#{pt.floor},width=#{pt.width},height=#{pt.height},bathroom=#{pt.bathroom},parking=#{pt.parking} " +
            "WHERE id = #{pt.id} AND project_id=#{proID}")
    Integer updatePropertyType(@Param("pt")PropertyType propertyType,@Param("proID")int projectID);

    @Delete("DELETE FROM property_type " +
            "WHERE id=#{id}")
    void removePropertyType(@Param("id")int id,@Param("proID")int projectID);

    @Select("SELECT id " +
            "FROM tower_type " +
            "WHERE project_id=#{proID}")
    List<Integer> findAllTowerTypeID(@Param("proID")int projectID);

    @Update("UPDATE tower_type " +
            "SET type=#{ty.type} " +
            "WHERE id=#{ty.id}")
    Integer updateTowerType(@Param("ty")TowerType towerType,@Param("proID")int projectID);

    @Delete("DELETE FROM tower_type " +
            "WHERE id=#{id}")
    void removeTowerType(@Param("id")int id,@Param("proID")int projectID);

    class ProjectUtil {
        public String updateProject(@Param("pro")ProjectRequest projectRequest){
            return new SQL(){
                {
                    UPDATE("project");
                    SET("city=#{pro.city},name=#{pro.name},grr=#{pro.grr},country_id=#{pro.countryID},project_type_id=#{pro.projectTypeID},completed_date=#{pro.completedDate},built_date=#{pro.builtDate},description=#{pro.description},start_price=#{pro.minPrice},end_price=#{pro.maxPrice},avg_rent_from=#{pro.avgRentFrom},avg_rent_to=#{pro.avgRentTo},down_payment=#{pro.downPayment},rent_or_buy=#{pro.status},address_1=#{pro.addressOne},address_2=#{pro.addressTwo}");
                    WHERE("id=#{pro.id}");
                }
            }.toString();
        }
        public String findAllProject(@Param("title")String title,@Param("cid") int cid, @Param("pid") int pid, @Param("status") String status, @Param("limit") int limit,@Param("offset")int offset){
            return new SQL(){
                {
                    SELECT("id,name,start_price,end_price,grr,country_id,project_type_id,thumbnail,isdisplay");
                    FROM("project");
                    WHERE("country_id=#{cid}");
                    if(title!=null) {
                        WHERE("name ilike '%'||#{title}||'%'");
                    }
                    if(status.equalsIgnoreCase("true"))
                        WHERE("isdisplay IS TRUE");
                    else if(status.equalsIgnoreCase("false"))
                        WHERE("isdisplay IS false");
                    if(pid>0)
                        WHERE("project_type_id=#{pid}");
                    ORDER_BY("id DESC LIMIT #{limit} OFFSET #{offset}");
                }
            }.toString();
        }
        public String findAllProjectCount(@Param("title")String title,@Param("cid") int cid, @Param("pid") int pid, @Param("status") String status){
            return new SQL(){
                {
                    SELECT("COUNT(id)");
                    FROM("project");
                    if(title!=null) {
                        WHERE("name ilike '%'||#{title}||'%'");
                    }
                    WHERE("country_id=#{cid}");
                    if(status.equalsIgnoreCase("true"))
                        WHERE("isdisplay IS TRUE");
                    else if(status.equalsIgnoreCase("false"))
                        WHERE("isdisplay IS false");
                    if(pid>0)
                        WHERE("project_type_id=#{pid}");
                }
            }.toString();
        }
        public String updateThumbnail(@Param("thumbnail") String thumbnail, @Param("proID") int projectID){
            return new SQL(){
                {
                    UPDATE("project");
                    SET("thumbnail = #{thumbnail}");
                    WHERE("id=#{proID}");
                }
            }.toString();
        }
        public String insertGallery(@Param("gall") String galleries, @Param("proID") int projectID){
            return new SQL(){
                {
                    INSERT_INTO("project_gallery");
                    VALUES("url,type,project_id","#{gall},'image',#{pid}");
                }
            }.toString();
        }
        public String findOneProject(@Param("proID") int projectID) {
            return new SQL() {
                {
                    SELECT("*");
                    FROM("project");
                    WHERE("id=#{proID}");
                }
            }.toString();
        }
        public String insertProject(@Param("project") ProjectRequest project) {
            return new SQL() {
                {
                    INSERT_INTO("project");
                    VALUES("id,city,name,grr,country_id,project_type_id,completed_date,built_date,description,start_price,end_price,avg_rent_from,avg_rent_to,down_payment,rent_or_buy,address_1,address_2,rent_or_buy", "#{id.id},#{project.city},#{project.name},#{project.grr},#{project.countryID},#{project.projectTypeID},#{project.completedDate},#{project.builtDate},#{project.description},#{project.minPrice},#{project.maxPrice},#{project.avgRentFrom},#{project.avgRentTo},#{project.downPayment},#{project.status},#{project.addressOne},#{project.addressTwo},#{project.status}");
                }
            }.toString();
        }
        public String insertProjectIntro(@Param("intro") ProjectIntroduction intro, @Param("proID") int projectID) {
            return new SQL() {
                {
                    INSERT_INTO("project_intro");
                    VALUES("name,description,project_id", "#{intro.name},#{intro.description},#{proID}");
                }
            }.toString();
        }
        public String insertPropertyType(@Param("pt") PropertyType propertyType, @Param("proID") int projectID) {
            return new SQL() {
                {
                    INSERT_INTO("property_type");
                    VALUES("name,bedroom,floor,width,height,bathroom,parking,project_id", "#{pt.type},#{pt.bedroom},#{pt.floor},#{pt.width},#{pt.height},#{pt.bathroom},#{pt.parking},#{proID}");
                }
            }.toString();
        }
        public String insertTowerType(@Param("type")String type,@Param("proID") int projectID) {
            return new SQL() {
                {
                    INSERT_INTO("tower_type");
                    VALUES("type,project_id", "#{type},#{proID}");
                }
            }.toString();
        }
    }

    class ProjectTypeForWeb {
        private int id;
        private String type;
        @JsonProperty("data")
        private List<Project> projectList;
        @JsonProperty("paging")
        private Pagination pagination;

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

        public List<Project> getProjectList() {
            return projectList;
        }

        public void setProjectList(List<Project> projectList) {
            this.projectList = projectList;
        }

        public Pagination getPagination() {
            return pagination;
        }

        public void setPagination(Pagination pagination) {
            this.pagination = pagination;
        }
    }

    class CountryForWeb {
        @JsonProperty("country_id")
        private int countryID;
        @JsonProperty("country_name")
        private String countryName;
        @JsonProperty("project_type")
        private List<ProjectTypeForWeb> projectTypeForWebList;

        public int getCountryID() {
            return countryID;
        }

        public void setCountryID(int countryID) {
            this.countryID = countryID;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public List<ProjectTypeForWeb> getProjectTypeForWebList() {
            return projectTypeForWebList;
        }

        public void setProjectTypeForWebList(List<ProjectTypeForWeb> projectTypeForWebList) {
            this.projectTypeForWebList = projectTypeForWebList;
        }
    }

    class ProjectListingReponse{
        private int id;
        private String name;
        @JsonProperty("start_price")
        private double startPrice;
        @JsonProperty("end_price")
        private double endPrice;
        private double grr;
        private String country;
        @JsonProperty("project_type")
        private String projectType;
        private String thumbnail;
        private boolean status;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getId() {
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

        public double getGrr() {
            return grr;
        }

        public void setGrr(double grr) {
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
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }
    }

    class ProjectListingRequest{
        @JsonProperty("country_id")
        private int countryID;
        @JsonProperty("project_type_id")
        private int projectTypID;
        private String status;

        public int getCountryID() {
            return countryID;
        }

        public void setCountryID(int countryID) {
            this.countryID = countryID;
        }

        public int getProjectTypID() {
            return projectTypID;
        }

        public void setProjectTypID(int projectTypID) {
            this.projectTypID = projectTypID;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    class Project {
        private int id;
        private String status;
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
        private List<ProjectIntroduction> projectIntro;
        @JsonProperty("galleries")
        private List<ProjectGallery> projectGalleries;
        @JsonProperty("property_types")
        private List<PropertyType> propertyTypes;
        @JsonProperty("tower_type")
        private List<TowerType> towerTypes;
        private boolean favorite;
        @JsonProperty("built_date")
        private Date builtDate;
        @JsonProperty("completed_date")
        private Date completedDate;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<TowerType> getTowerTypes() {
            return towerTypes;
        }

        public void setTowerTypes(List<TowerType> towerTypes) {
            this.towerTypes = towerTypes;
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

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public Double getGrr() {
            return grr;
        }

        public void setGrr(Double grr) {
            this.grr = grr;
        }

        public List<ProjectIntroduction> getProjectIntro() {
            return projectIntro;
        }

        public void setProjectIntro(List<ProjectIntroduction> projectIntro) {
            this.projectIntro = projectIntro;
        }

        public List<ProjectGallery> getProjectGalleries() {
            return projectGalleries;
        }

        public void setProjectGalleries(List<ProjectGallery> projectGalleries) {
            this.projectGalleries = projectGalleries;
        }

        public List<PropertyType> getPropertyTypes() {
            return propertyTypes;
        }

        public void setPropertyTypes(List<PropertyType> propertyTypes) {
            this.propertyTypes = propertyTypes;
        }

        public boolean isFavorite() {
            return favorite;
        }

        public void setFavorite(boolean favorite) {
            this.favorite = favorite;
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
    }

    class TowerType{
        private int id;
        @JsonProperty("project_id")
        private int projectID;
        private String type;

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

        public int getProjectID() {
            return projectID;
        }

        public void setProjectID(int projectID) {
            this.projectID = projectID;
        }
    }

    class ProjectGallery {
        private String image;

        public String getImage() {
            if (image != null)
                return Url.projectGalleryUrl + image;
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

    }

    class PropertyType {
        private int id;
        private String type;
        private Integer floor;
        private Double width;
        private Double height;
        private Integer bedroom;
        private Integer bathroom;
        private Integer parking;

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

    }

    class ProjectIntroduction {
        private int id;
        private String name;
        private String description;

        public int getId() {
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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

    }

    class ProjectRequest {
        private int id;
        @JsonProperty("title")
        private String name;
        @JsonProperty("built_date")
        private Date builtDate;
        @JsonProperty("completed_date")
        private Date completedDate;
        @JsonProperty("project_type_id")
        private Integer projectTypeID;
        @JsonProperty("project_type")
        private String projectType;
        @JsonProperty("country")
        private String country;
        @NotNull
        private double grr;
        @JsonProperty("down_payment")
        private String downPayment;
        private String description;
        @NotNull
        @JsonProperty("country_id")
        private int countryID;
        @JsonProperty("address_1")
        private String addressOne;
        @JsonProperty("address_2")
        private String addressTwo;
        @NotNull
        @NotEmpty
        private String status;
        @NotNull
        @NotEmpty
        private String city;
        @NotNull
        @JsonProperty("min_price")
        private double minPrice;
        @NotNull
        @JsonProperty("max_price")
        private double maxPrice;
        @NotNull
        @JsonProperty("avg_annual_rent_from")
        private double avgRentFrom;
        @NotNull
        @JsonProperty("avg_annual_rent_to")
        private double avgRentTo;
        @JsonProperty("introductions")
        private List<ProjectIntroduction> projectIntroductions;
        @JsonProperty("property_types")
        private List<PropertyType> propertyTypes;
        @JsonProperty("tower_types")
        private List<TowerType> towerTypes;

        public int getId() {
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

        public Integer getProjectTypeID() {
            return projectTypeID;
        }

        public void setProjectTypeID(Integer projectTypeID) {
            this.projectTypeID = projectTypeID;
        }

        public String getProjectType() {
            return projectType;
        }

        public void setProjectType(String projectType) {
            this.projectType = projectType;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public double getGrr() {
            return grr;
        }

        public void setGrr(double grr) {
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

        public int getCountryID() {
            return countryID;
        }

        public void setCountryID(int countryID) {
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

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
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

        public double getAvgRentFrom() {
            return avgRentFrom;
        }

        public void setAvgRentFrom(double avgRentFrom) {
            this.avgRentFrom = avgRentFrom;
        }

        public double getAvgRentTo() {
            return avgRentTo;
        }

        public void setAvgRentTo(double avgRentTo) {
            this.avgRentTo = avgRentTo;
        }

        public List<ProjectIntroduction> getProjectIntroductions() {
            return projectIntroductions;
        }

        public void setProjectIntroductions(List<ProjectIntroduction> projectIntroductions) {
            this.projectIntroductions = projectIntroductions;
        }

        public List<PropertyType> getPropertyTypes() {
            return propertyTypes;
        }

        public void setPropertyTypes(List<PropertyType> propertyTypes) {
            this.propertyTypes = propertyTypes;
        }

        public List<TowerType> getTowerTypes() {
            return towerTypes;
        }

        public void setTowerTypes(List<TowerType> towerTypes) {
            this.towerTypes = towerTypes;
        }
    }
}
