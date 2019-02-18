package com.century21.service.api_allcity;

import com.century21.exception.CustomRuntimeException;
import com.century21.repository.api_allcity.CityRepo;
import com.century21.repository.api_allcity.Country;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private CityRepo cityRepo;
    @Override
    public List<Country> allCity() {
        List<Country> cities = cityRepo.countries();
        if(cities.size()<1) throw new CustomRuntimeException(404,"ZERO RESULT");
        return cities;
    }
}
