package com.century21.century21cambodia.service.api_update_project;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_update_project.UpdateProj;
import com.century21.century21cambodia.repository.api_update_project.UpdateProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateProjectServiceImpl implements UpdateProjectService {

    @Autowired
    private UpdateProjectRepo updateProjectRepo;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateProject(UpdateProj updateProj) {
        Integer countryID=updateProjectRepo.findCountryID(updateProj.getCountry());
        Integer projectTypeID=updateProjectRepo.findProjectTypeID(updateProj.getProjectType());

        if(countryID==null || countryID<1 ) throw new CustomRuntimeException(404,"country not found.");
        if(projectTypeID==null || projectTypeID<1) throw new CustomRuntimeException(404,"project type not found.");

        if(updateProjectRepo.updateProject(updateProj,countryID,projectTypeID)<1) throw new CustomRuntimeException(500,"can not update project");

        if(updateProj.getProjectIntro()!=null) {
            for (int i = 0; i < updateProj.getProjectIntro().size(); i++) {
                System.out.println(updateProj.getProjectIntro().get(i).getId());
                updateProjectRepo.updateIntroduction(updateProj.getProjectIntro().get(i).getId(),updateProj.getProjectID(), updateProj.getProjectIntro().get(i).getName(), updateProj.getProjectIntro().get(i).getDescription());
            }
        }
    }
}
