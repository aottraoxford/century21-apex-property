package com.century21.century21cambodia.service.api_project_details;

import com.century21.century21cambodia.repository.api_project_details.ProjectDetail;

import java.security.Principal;

public interface ProjectDetailService {
    ProjectDetail projectDetails(int projectID, Principal principal);
}
