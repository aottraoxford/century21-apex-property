package com.century21.service;

import com.century21.model.ID;
import com.century21.repository.PropertyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PropertyServiceImpl implements PropertyService{
    @Autowired
    private PropertyRepo propertyRepo;
    @Override
    public int insertProperty(PropertyRepo.PropertyRequest propertyRequest) {
        ID id = new ID();
        propertyRepo.insertProperty(id,propertyRequest);
        return id.getId();
    }
}
