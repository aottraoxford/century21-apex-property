package com.century21.service.api_project_details;

import com.century21.exception.CustomRuntimeException;
import com.century21.repository.api_project_details.ProjectDetail;
import com.century21.repository.api_project_details.ProjectDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ProjectDetailServiceImpl implements ProjectDetailService {
    @Autowired
    private ProjectDetailRepo projectDetailRepo;

    @Override
    public ProjectDetail projectDetails(int projectID, Principal principal) {
        ProjectDetail projectDetail = projectDetailRepo.projectDetail(projectID);
        projectDetail.setId(projectID);
        if (projectDetail == null) throw new CustomRuntimeException(404, "PROJECT ID NOT FOUND");


        Integer userID = null;
        if (principal != null) {
            userID = projectDetailRepo.getUserIDByEmail(principal.getName());
        }
        if (userID != null) {
            if (projectDetailRepo.favorite(projectID, (int) userID) != null) projectDetail.setFavorite(true);
        }


        return projectDetail;
    }
}
