package com.century21.apexproperty.service.api_slider;

import com.century21.apexproperty.repository.api_slider.Slider;

import java.util.List;

public interface SliderService {
    List<Slider> getSlider(String enable);
}
