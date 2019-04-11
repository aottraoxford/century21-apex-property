package com.century21.apexproperty.service.api_visible_project;

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
    public void visibleProject(boolean status, int projectID,String token) {
        ProjectRepo.ProjectNoti projectNoti=projectRepo.projectNoti(projectID);
        if(visibleProjectRepo.visibleProject(status,projectID)<1) throw new CustomRuntimeException(404,"project not found.");
        if(status && !projectNoti.isIsdisplay()) {
            myNotification.sendToAllSubscriber(projectNoti.getName(), projectNoti.getDescription(),projectNoti.getThumbnail(),token,"project",projectID );
        }
    }
}