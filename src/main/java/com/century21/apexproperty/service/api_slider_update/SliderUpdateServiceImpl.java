package com.century21.apexproperty.service.api_slider_update;

import com.century21.apexproperty.configuration.upload.FileUploadProperty;
import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.api_slider_update.SliderUpdateRepo;
import com.century21.apexproperty.configuration.upload.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SliderUpdateServiceImpl implements SliderUpdateService {
    @Autowired
    private SliderUpdateRepo sliderUpdateRepo;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private FileUploadProperty fileUploadProperty;

    @Override
    public Slider updateSlider(boolean enable, int sliderID, MultipartFile file) {
        int id = sliderUpdateRepo.checkID(sliderID);
        if (id < 1)
            throw new CustomRuntimeException(404, "invalid ID");
        String fileName = null;
        if (file != null) {
            fileUploadService.removeImage(file.getOriginalFilename(), fileUploadProperty.getSlider());
            fileName = fileUploadService.storeImage(file, fileUploadProperty.getSlider());
        } else {
            fileName = sliderUpdateRepo.findImage(sliderID);
        }
        sliderUpdateRepo.sliderUpdate(enable, fileName, sliderID);
        Slider slider = new Slider();
        slider.setId(sliderID);
        slider.setSlider(fileName);
        return slider;
    }


}
