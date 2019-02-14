package com.century21.century21cambodia.service.api_update_project;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_update_project.UpdateProj;
import com.century21.century21cambodia.repository.api_update_project.UpdateProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class UpdateProjectServiceImpl implements UpdateProjectService {

    @Autowired
    private UpdateProjectRepo updateProjectRepo;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateProject(UpdateProj updateProj) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dateFormat.parse(updateProj.getBuiltDate());
            dateFormat.parse(updateProj.getCompletedDate());
        } catch(Exception e) {
            throw new CustomRuntimeException(400,e.getMessage());
        }

        Integer countryID=updateProjectRepo.findCountryID(updateProj.getCountry());
        Integer projectTypeID=updateProjectRepo.findProjectTypeID(updateProj.getProjectType());

        if(countryID==null || countryID<1 ){
            countryID=updateProjectRepo.insertCountry(updateProj.getCountry());
        }
        if(projectTypeID==null || projectTypeID<1){
            projectTypeID=updateProjectRepo.insertProjectType(updateProj.getProjectType());
        }

        if(updateProjectRepo.updateProject(updateProj,countryID,projectTypeID)==null) throw new CustomRuntimeException(400,"can not update project");

        if(updateProj.getProjectIntro()!=null) {
            for (int i = 0; i < updateProj.getProjectIntro().size(); i++) {
                updateProjectRepo.updateIntroduction(updateProj.getProjectIntro().get(i).getId(),updateProj.getProjectID(), updateProj.getProjectIntro().get(i).getName(), updateProj.getProjectIntro().get(i).getDescription());
            }
        }
    }
}
