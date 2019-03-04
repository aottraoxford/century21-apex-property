package com.century21.service;

import com.century21.repository.PropertyRepo;

public interface PropertyService {
    int insertProperty(PropertyRepo.PropertyRequest propertyRequest);
}
