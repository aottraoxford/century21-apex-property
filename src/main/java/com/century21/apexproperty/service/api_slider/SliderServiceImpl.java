package com.century21.apexproperty.service.api_slider;

import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_slider.Slider;
import com.century21.apexproperty.repository.api_slider.SliderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SliderServiceImpl implements SliderService {
    @Autowired
    private SliderRepo sliderRepo;
    @Override
    public List<Slider> getSlider(boolean enable) {
        List<Slider> slider=sliderRepo.getSlider(enable);
        if(slider==null || slider.size()<1) throw new CustomRuntimeException(404,"ZERO RECORD");
        return slider;
    }
}
