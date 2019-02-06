package com.century21.century21cambodia.service.api_slider;

import com.century21.century21cambodia.repository.api_slider.Slider;

import java.util.List;

public interface SliderService {
    List<Slider> getSlider(boolean enable);
}
