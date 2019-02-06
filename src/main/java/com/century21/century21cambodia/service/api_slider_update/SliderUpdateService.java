package com.century21.century21cambodia.service.api_slider_update;

import org.springframework.web.multipart.MultipartFile;

public interface SliderUpdateService {
    Slider updateSlider(boolean enable, int sliderID, MultipartFile file);
}
