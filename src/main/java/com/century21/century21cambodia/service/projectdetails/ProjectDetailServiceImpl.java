package com.century21.century21cambodia.service.projectdetails;

import com.century21.century21cambodia.repository.project_details.ProjectDetail;
import com.century21.century21cambodia.repository.project_details.ProjectDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectDetailServiceImpl implements ProjectDetailService {
    @Autowired
    private ProjectDetailRepo projectDetailRepo;

    @Override
    public ProjectDetail projectDetails(int projectID) {
        return projectDetailRepo.projectDetail(projectID);
    }
}
