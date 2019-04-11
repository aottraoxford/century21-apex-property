package com.century21.apexproperty.repository.api_slider;

import com.century21.apexproperty.util.Url;

public class Slider {
    private int id;
    private String title;
    private String slider;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlider() {
        if(slider!=null)
            return Url.sliderUrl+slider;
        return slider;
    }

    public void setSlider(String slider) {
        this.slider = slider;
    }

    @Override
    public String toString() {
        return "Slider{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", slider='" + slider + '\'' +
                '}';
    }
}
