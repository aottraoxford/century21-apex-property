package com.century21.service.api_project_details;

import com.century21.repository.api_project_details.ProjectDetail;

import java.security.Principal;

public interface ProjectDetailService {
    ProjectDetail projectDetails(int projectID, Principal principal);
}
