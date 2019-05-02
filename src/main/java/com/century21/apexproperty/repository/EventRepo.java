package com.century21.apexproperty.repository;

import com.century21.apexproperty.model.ID;
import com.century21.apexproperty.util.Url;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


@Repository
public interface EventRepo {

    @InsertProvider(type = EventUtil.class,method = "insertEvent")
    @SelectKey(statement ="select nextval('events_id_seq') ", resultType = int.class,before = true, keyProperty = "id.id")
    int insertEvent(@Param("id") ID id, @Param("en")EventRequest en, @Param("banner")String banner, @Param("date")Timestamp date);

    @SelectProvider(type = EventUtil.class, method = "findAllEvent")
    @Results({
            @Result(property = "status", column = "enable"),
            @Result(property = "eventDate",column = "event_date")
    })
    List<EventResponse> findAllEvent(@Param("title")String title,@Param("status") String status,@Param("limit")int limit,@Param("offset")int offset);

    @SelectProvider(type = EventUtil.class, method = "findOneEvent")
    @Results({
            @Result(property = "status", column = "enable"),
            @Result(property = "eventDate",column = "event_date")
    })
    EventResponse findOneEvent(@Param("eventID") int eventID);

    @SelectProvider(type = EventUtil.class, method = "changeEventStatus")
    void changeEventStatus(@Param("eventID") int eventID, @Param("status") boolean status);

    @SelectProvider(type = EventUtil.class,method = "findAllEventCount")
    int findAllEventCount(@Param("title")String title,@Param("status")String status);

    class EventUtil {
        public String insertEvent(@Param("id") ID id, @Param("en")EventRequest en, @Param("banner")String banner, @Param("date")Timestamp date){
            return new SQL(){
                {
                    INSERT_INTO("events");
                    VALUES("id,title,banner,description,message,event_date,type,enable","#{id.id},#{en.title},#{banner},#{en.description},#{en.message},#{date},'event',false");
                }
            }.toString();
        }

        public String findAllEvent(@Param("title")String title,@Param("status") String status,@Param("limit")int limit,@Param("offset")int offset) {
            return new SQL() {
                {
                    SELECT("id,title,description,banner,enable,event_date,message");
                    FROM("events");
                    WHERE("type ilike 'event'");
                    if(title!=null && title.trim().length()>0){
                        WHERE("title ILIKE '%'||#{title}||'%'");
                    }
                    if (status.equalsIgnoreCase("true"))
                        WHERE("enable IS TRUE");
                    else if (status.equalsIgnoreCase("false"))
                        WHERE("enable IS FALSE");
                    ORDER_BY("id DESC limit #{limit} offset #{offset}");
                }
            }.toString();
        }

        public String findAllEventCount(@Param("title")String title,@Param("status") String status){
            return new SQL(){
                {
                    SELECT("COUNT(id)");
                    FROM("events");
                    WHERE("type ILIKE 'event'");
                    if(title!=null && title.trim().length()>0){
                        WHERE("title ILIKE '%'||#{title}||'%'");
                    }
                    if (status.equalsIgnoreCase("true"))
                        WHERE("enable IS TRUE");
                    else if (status.equalsIgnoreCase("false"))
                        WHERE("enable IS FALSE");
                }
            }.toString();
        }

        public String findOneEvent(@Param("eventID") int eventID) {
            return new SQL() {
                {
                    SELECT("id,title,banner,enable,event_date,description,message");
                    FROM("events");
                    WHERE("type ILIKE 'event'");
                    WHERE("id=#{eventID}");
                }
            }.toString();
        }

        public String changeEventStatus(@Param("eventID") int eventID, @Param("status") boolean status) {
            return new SQL() {
                {
                    UPDATE("events");
                    if (status)
                        SET("enable=true");
                    else SET("enable=false");
                    WHERE("id=#{eventID}");
                }
            }.toString();
        }

    }

    class EventRequest {
        @NotNull
        @NotEmpty
        @NotBlank
        private String title;
        @NotNull
        @NotEmpty
        @NotBlank
        private String message;
        @NotNull
        @NotEmpty
        @NotBlank
        private String event_date;
        private String description;
        private MultipartFile file;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getEvent_date() {
            return event_date;
        }

        public void setEvent_date(String event_date) {
            this.event_date = event_date;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public MultipartFile getFile() {
            return file;
        }

        public void setFile(MultipartFile file) {
            this.file = file;
        }
    }


    class EventResponse {
        private int id;
        private String title;
        private String banner;
        private String message;
        private boolean status;
        @JsonProperty("expired_date")
        private long expiredDate;
        @JsonIgnore
        private Date eventDate;
        private String description;

        @Override
        public String toString() {
            return "EventResponse{" +
                    "id=" + id +
                    ", title='" + title + '\'' +
                    ", banner='" + banner + '\'' +
                    ", message='" + message + '\'' +
                    ", status=" + status +
                    ", expiredDate=" + expiredDate +
                    ", eventDate=" + eventDate +
                    ", description='" + description + '\'' +
                    '}';
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public long getExpiredDate() {
            return eventDate.getTime();
        }

        public void setExpiredDate(long expiredDate) {
            this.expiredDate = expiredDate;
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


        public String getBanner() {
            if (banner != null)
                return Url.bannerUrl + banner;
            return banner;
        }

        public void setBanner(String banner) {
            this.banner = banner;
        }

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public Date getEventDate() {
            return eventDate;
        }

        public void setEventDate(Date eventDate) {
            this.eventDate = eventDate;
        }

    }
}
