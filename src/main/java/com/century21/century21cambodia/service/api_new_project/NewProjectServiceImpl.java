package com.century21.century21cambodia.service.api_new_project;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_new_project.Country;
import com.century21.century21cambodia.repository.api_new_project.NewProjectRepo;
import com.century21.century21cambodia.repository.api_new_project.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NewProjectServiceImpl implements NewProjectService {
    @Autowired
    private NewProjectRepo newProjectRepo;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int createNewProject(Project project) {
        int r;
        try {
            int projectID = newProjectRepo.saveProject(project);
            r=projectID;
            if(project.getProjectIntroductions()!=null) {
                for (int i = 0; i < project.getProjectIntroductions().size(); i++) {
                    newProjectRepo.saveProjectIntroduction(project.getProjectIntroductions().get(i), projectID);
                }
            }
            if(project.getProjectTypeID()==2){
                newProjectRepo.saveTowerType(projectID);
            }else{
                if(project.getPropertyTypes()!=null) {
                    for (int i = 0; i < project.getPropertyTypes().size(); i++) {
                        newProjectRepo.savePropertyType(project.getPropertyTypes().get(i), projectID);
                    }
                }
            }
        }catch (Exception e){
            throw new CustomRuntimeException(500,e.getMessage());
        }
        return r;
    }

    @Override
    public List<Country> countries(String name) {
        List<Country> countries=newProjectRepo.countries(name);
        if(countries==null || countries.size()<1)
           throw new CustomRuntimeException(404,"ZERO RECORD");
        return countries;
    }
}
