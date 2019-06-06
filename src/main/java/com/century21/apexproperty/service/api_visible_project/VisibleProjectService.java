package com.century21.apexproperty.service.api_visible_project;

import com.century21.apexproperty.model.request.Notification;

public interface VisibleProjectService {
    void visibleProject(Notification noti, boolean status, int projectID);
}
