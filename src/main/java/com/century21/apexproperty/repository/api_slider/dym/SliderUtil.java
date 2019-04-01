package com.century21.apexproperty.repository.api_slider.dym;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public class SliderUtil {
    public String getSlider(@Param("en")boolean enable){
        return new SQL(){
            {
                SELECT("id,title,banner");
                FROM("events");
                WHERE("type ILIKE 'slider'");
                if(enable==true)
                    WHERE("enable IS true");
                else
                    WHERE("enable IS false");
                ORDER_BY("id DESC");
            }
        }.toString();
    }
}
