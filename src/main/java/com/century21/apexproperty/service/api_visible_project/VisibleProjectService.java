package com.century21.apexproperty.service.api_visible_project;

import com.century21.apexproperty.controller.ProjectController;

public interface VisibleProjectService {
    void visibleProject(ProjectController.Noti noti, boolean status, int projectID);
}
