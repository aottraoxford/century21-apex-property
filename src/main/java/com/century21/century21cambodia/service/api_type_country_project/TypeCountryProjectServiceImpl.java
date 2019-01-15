package com.century21.century21cambodia.service.api_type_country_project;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_type_country_project.TypeCountryProject;
import com.century21.century21cambodia.repository.api_type_country_project.TypeCountryProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeCountryProjectServiceImpl implements TypeCountryProjectService {
    @Autowired
    private TypeCountryProjectRepo typeCountryProjectRepo;
    @Override
    public List<TypeCountryProject> typeCP() {
        List<TypeCountryProject> typeCountryProjects=typeCountryProjectRepo.typeCP();
        if(typeCountryProjects==null || typeCountryProjects.size()<1) throw new CustomRuntimeException(404,"ZERO RESULT");
        return typeCountryProjects;
    }
}
