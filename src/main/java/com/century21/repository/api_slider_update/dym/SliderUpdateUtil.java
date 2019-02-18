package com.century21.repository.api_slider_update.dym;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SliderUpdateUtil {
    public String updateSlider(@Param("enable")boolean enable,@Param("image")String image,@Param("sliderID")int eventID){
        return new SQL(){
            {
                UPDATE("events");
                if (enable == true)
                    SET("enable = true");
                else SET("enable = false");
                if(image!=null)
                    SET("banner = #{image}");
                WHERE("id=#{sliderID}");
                ORDER_BY("id DESC");

            }
        }.toString();
    }
}
