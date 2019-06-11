package com.century21.apexproperty.service.api_visible_project;

import com.century21.apexproperty.controller.ProjectController;
import com.century21.apexproperty.model.request.Notification;
import com.century21.apexproperty.util.MyNotification;
import com.century21.apexproperty.exception.CustomRuntimeException;
import com.century21.apexproperty.repository.ProjectRepo;
import com.century21.apexproperty.repository.api_visible_project.VisibleProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisibleProjectServiceImpl implements VisibleProjectService {
    @Autowired
    private VisibleProjectRepo visibleProjectRepo;
    @Autowired
    private MyNotification myNotification;
    @Autowired
    private ProjectRepo projectRepo;

    @Override
    public void visibleProject(Notification noti, boolean status, int projectID) {
        ProjectRepo.ProjectNoti projectNoti = projectRepo.projectNoti(projectID);
        if (visibleProjectRepo.visibleProject(status, projectID) < 1)
            throw new CustomRuntimeException(404, "project not found.");
        if (status && !projectNoti.isIsdisplay()) {
            myNotification.sendToAllSubscriber(projectNoti.getName(), noti.getMessage(), projectNoti.getThumbnail(), "project", projectID);
        }
    }
}
