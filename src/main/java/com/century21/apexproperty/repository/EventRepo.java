package com.century21.apexproperty.repository;

import com.century21.apexproperty.model.ID;
import com.century21.apexproperty.util.Url;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
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

    @Select("delete from events where type ilike 'event' and id=#{id} returning id,banner")
    EventResponse removeEventById(Integer id);

    @InsertProvider(type = EventUtil.class, method = "insertEvent")
    @SelectKey(statement = "select nextval('events_id_seq') ", resultType = int.class, before = true, keyProperty = "id.id")
    int insertEvent(@Param("id") ID id, @Param("en") EventRequest en, @Param("banner") String banner, @Param("date") Timestamp date);

    @SelectProvider(type = EventUtil.class, method = "findAllEvent")
    @Results({
            @Result(property = "status", column = "enable"),
            @Result(property = "eventDate", column = "event_date")
    })
    List<EventResponse> findAllEvent(@Param("title") String title, @Param("status") String status, @Param("limit") int limit, @Param("offset") int offset);

    @SelectProvider(type = EventUtil.class, method = "findOneEvent")
    @Results({
            @Result(property = "status", column = "enable"),
            @Result(property = "eventDate", column = "event_date")
    })
    EventResponse findOneEvent(@Param("eventID") int eventID);

    @SelectProvider(type = EventUtil.class, method = "changeEventStatus")
    void changeEventStatus(@Param("eventID") int eventID, @Param("status") boolean status);

    @SelectProvider(type = EventUtil.class, method = "findAllEventCount")
    int findAllEventCount(@Param("title") String title, @Param("status") String status);

    class EventUtil {
        public String insertEvent(@Param("id") ID id, @Param("en") EventRequest en, @Param("banner") String banner, @Param("date") Timestamp date) {
            return new SQL() {
                {
                    INSERT_INTO("events");
                    VALUES("id,title,banner,description,message,event_date,type,enable", "#{id.id},#{en.title},#{banner},#{en.description},#{en.message},#{date},'event',false");
                }
            }.toString();
        }

        public String findAllEvent(@Param("title") String title, @Param("status") String status, @Param("limit") int limit, @Param("offset") int offset) {
            return new SQL() {
                {
                    SELECT("id,title,description,banner,enable,event_date,message");
                    FROM("events");
                    WHERE("type ilike 'event'");
                    if (title != null && title.trim().length() > 0) {
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

        public String findAllEventCount(@Param("title") String title, @Param("status") String status) {
            return new SQL() {
                {
                    SELECT("COUNT(id)");
                    FROM("events");
                    WHERE("type ILIKE 'event'");
                    if (title != null && title.trim().length() > 0) {
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
        @ApiModelProperty(example = "Century 21 Cambodia honours local franchises")
        @NotNull
        @NotEmpty
        @NotBlank
        private String title;
        @ApiModelProperty(example = "On July 14th 2018, CENTURY 21 Cambodia, master franchise of CENTURY 21 in the kingdom, held an award ceremony to celebrate its success at the Great Duke Phnom Penh Hotel. 8 of CENTURY 21 Cambodia franchisees received awards for their outstanding performance from CENTURY 21 Real Estate LLC. ")
        @NotNull
        @NotEmpty
        @NotBlank
        private String message;
        @ApiModelProperty(example = "2019-10-25 10:40")
        @NotNull
        @NotEmpty
        @NotBlank
        private String event_date;
        @ApiModelProperty(example = "<p style=\"margin-bottom: 15px; font-family: &quot;Droid Serif&quot;; font-size: 18px; color: rgb(50, 50, 50);\">Century 21 Cambodia yesterday held an awards ceremony to honour its local franchises.</p><div class=\"khmer-content_7\" id=\"khmer-1867554569\" style=\"color: rgb(50, 50, 50); font-family: &quot;open sans&quot;, sans-serif; font-size: 13px;\"><p style=\"margin-bottom: 0px; font-family: &quot;Droid Serif&quot;; font-size: 18px; text-align: center;\"><span style=\"color: rgb(0, 51, 102);\"><em>For in depth analysis of Cambodian Business, visit&nbsp;</em><span style=\"margin-bottom: 0px; color: rgb(248, 0, 0);\"><span style=\"font-weight: 700; margin-bottom: 0px;\"><a href=\"https://capitalcambodia.com/\" style=\"transition: color 0.18s ease 0s, background-color 0.18s ease 0s, border-color 0.18s ease 0s; outline: none; color: rgb(248, 0, 0); background-image: initial; background-position: 0px 0px; background-size: initial; background-repeat: initial; background-attachment: initial; background-origin: initial; background-clip: initial; margin-bottom: 0px;\">Capital Cambodia</a></span></span></span><br><span style=\"margin-bottom: 0px; color: rgb(255, 255, 255);\">.</span></p></div><p style=\"margin-bottom: 15px; font-family: &quot;Droid Serif&quot;; font-size: 18px; color: rgb(50, 50, 50);\">During the event, the company also introduced a new logo.</p><p style=\"margin-bottom: 15px; font-family: &quot;Droid Serif&quot;; font-size: 18px; color: rgb(50, 50, 50);\">Speaking at the awards ceremony, held at Raffles Hotel Le Royal, Grace Rachny Fong, Century 21 CEO, said, “The new logo features a refreshed color palette that stays true to its iconic gold and black scheme, while also embracing new graphics.”</p><div class=\"khmer-content_3\" id=\"khmer-1302916007\" style=\"color: rgb(50, 50, 50); font-family: &quot;open sans&quot;, sans-serif; font-size: 13px;\"><span style=\"color: rgb(255, 255, 255);\">.</span>&nbsp;<span style=\"margin-bottom: 0px; color: rgb(255, 255, 255);\">.</span></div><p style=\"margin-bottom: 15px; font-family: &quot;Droid Serif&quot;; font-size: 18px; color: rgb(50, 50, 50);\">At the event, three local franchises received the ‘office award’ – Century 21 Land Station, Century 21 Fuji Realty, and Century 21 Regent Realty.</p><p style=\"margin-bottom: 15px; font-family: &quot;Droid Serif&quot;; font-size: 18px; color: rgb(50, 50, 50);\"><img class=\"aligncenter size-full wp-image-610073 ls-is-cached lazyloaded\" src=\"https://www.khmertimeskh.com/wp-content/uploads/2019/06/Mrw.jpg\" alt=\"\" width=\"750\" height=\"440\" srcset=\"https://www.khmertimeskh.com/wp-content/uploads/2019/06/Mrw.jpg 750w, https://www.khmertimeskh.com/wp-content/uploads/2019/06/Mrw-300x176.jpg 300w, https://www.khmertimeskh.com/wp-content/uploads/2019/06/Mrw-150x88.jpg 150w\" sizes=\"(max-width: 750px) 100vw, 750px\" data-srcset=\"https://www.khmertimeskh.com/wp-content/uploads/2019/06/Mrw.jpg 750w, https://www.khmertimeskh.com/wp-content/uploads/2019/06/Mrw-300x176.jpg 300w, https://www.khmertimeskh.com/wp-content/uploads/2019/06/Mrw-150x88.jpg 150w\" data-src=\"https://www.khmertimeskh.com/wp-content/uploads/2019/06/Mrw.jpg\" style=\"display: block; max-width: 100%; height: auto; margin: 7px auto 0px; opacity: 1; transition: none 0s ease 0s; animation-name: loaded; animation-duration: var(--lazy-loader-animation-duration);\"></p><p style=\"margin-bottom: 15px; font-family: &quot;Droid Serif&quot;; font-size: 18px; color: rgb(50, 50, 50);\">In the ‘agent award’ categories, the winners were Century 21 Golden Realty, Century 21 Dream Property, Century 21 Imperial Realty, Century 21 Mekong, and Century 21 Advanced Property.</p><p style=\"margin-bottom: 15px; font-family: &quot;Droid Serif&quot;; font-size: 18px; color: rgb(50, 50, 50);\">Century 21 Cambodia has a total of 22 franchises. It began operations in 2013, and so far has franchises in Phnom Penh and Preah Sihanouk province.</p><p style=\"margin-bottom: 15px; font-family: &quot;Droid Serif&quot;; font-size: 18px; color: rgb(50, 50, 50);\">“Every new achievement, it is not only a point of pride for Century 21 Cambodia, but also for Cambodia as a country,” Ms Grace Rachny Fong said.</p><p style=\"margin-bottom: 15px; font-family: &quot;Droid Serif&quot;; font-size: 18px; color: rgb(50, 50, 50);\">Chroek Soknim, CEO of Century 21 Mekong, said the award serves as motivation to continue the hard work.</p>")
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
