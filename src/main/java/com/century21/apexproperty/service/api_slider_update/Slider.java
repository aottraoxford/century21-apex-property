package com.century21.apexproperty.service.api_slider_update;

import com.century21.apexproperty.util.Url;

public class Slider {
    private int id;
    private String slider;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlider() {
        if (slider != null) return Url.sliderUrl + slider;
        return slider;
    }

    public void setSlider(String slider) {
        this.slider = slider;
    }

    @Override
    public String toString() {
        return "Slider{" +
                "id=" + id +
                ", slider='" + slider + '\'' +
                '}';
    }
}
