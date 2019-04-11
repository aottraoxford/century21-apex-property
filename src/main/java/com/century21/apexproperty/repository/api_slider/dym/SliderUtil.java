package com.century21.apexproperty.repository.api_slider.dym;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SliderUtil {
    public String getSlider(@Param("en")String en){
        return new SQL(){
            {
                SELECT("id,title,banner,enable");
                FROM("events");
                WHERE("type ILIKE 'slider'");
                if(en!=null && en.trim().length()>0) {
                    if(en.equalsIgnoreCase("true"))
                        WHERE("enable IS true");
                    else if(en.equalsIgnoreCase("false"))
                        WHERE("enable IS false");
                }
                ORDER_BY("id DESC");
            }
        }.toString();
    }

    public String getSliderCount(@Param("en")String en){
        return new SQL(){
            {
                SELECT("count(id)");
                FROM("events");
                WHERE("type ILIKE 'slider'");
                if(en!=null && en.trim().length()>0) {
                    if(en.equalsIgnoreCase("true"))
                        WHERE("enable IS true");
                    else if(en.equalsIgnoreCase("false"))
                        WHERE("enable IS false");
                }
            }
        }.toString();
    }
}
