package com.century21.service.api_type_country_project;

import com.century21.exception.CustomRuntimeException;
import com.century21.repository.api_type_country_project.ProjectType;
import com.century21.repository.api_type_country_project.TypeCountryProject;
import com.century21.repository.api_type_country_project.TypeCountryProjectRepo;
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
        for(int i=0;i<typeCountryProjects.size();i++){
            if(typeCountryProjects.get(i).getCountryID()==1){
                ProjectType projectType=new ProjectType();
                projectType.setProjectID(-1);
                projectType.setTypeName("General");
                typeCountryProjects.get(i).getTypes().add(typeCountryProjects.get(i).getTypes().size(), projectType);
                break;
            }
        }
        return typeCountryProjects;
    }
}
