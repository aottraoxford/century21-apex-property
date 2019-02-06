package com.century21.century21cambodia.service.api_slider_add;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_silder_add.AddSliderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddSliderServiceImpl implements AddSliderService {
    @Autowired
    private AddSliderRepo addSliderRepo;
    @Override
    public Integer addSlider(String name, String image) {
        System.out.println(image);
        Integer result = addSliderRepo.addSlider(name,image);
        if(result==null) throw new CustomRuntimeException(400,"CAN NOT ADD SLIDER");
        return result;
    }
}
