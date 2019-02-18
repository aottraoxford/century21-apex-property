package com.century21.service.api_slider;

import com.century21.repository.api_slider.Slider;

import java.util.List;

public interface SliderService {
    List<Slider> getSlider(boolean enable);
}
