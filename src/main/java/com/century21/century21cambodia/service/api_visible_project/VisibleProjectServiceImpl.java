package com.century21.century21cambodia.service.api_visible_project;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.repository.api_visible_project.VisibleProjectRepo;
import com.century21.century21cambodia.util.MyNotification;
import com.century21.century21cambodia.util.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VisibleProjectServiceImpl implements VisibleProjectService {
    @Autowired
    private VisibleProjectRepo visibleProjectRepo;
    @Autowired
    private MyNotification myNotification;
    @Override
    public void visibleProject(boolean status, int projectID,String token) {
        boolean projectStatus=visibleProjectRepo.checkProjectStatus(projectID);
        if(visibleProjectRepo.visibleProject(status,projectID)<1) throw new CustomRuntimeException(404,"project not found.");
        if(status==true && projectStatus==false) {
            String thumbnail=visibleProjectRepo.thumbnail(projectID);
            if(thumbnail==null)
                thumbnail="";
            else thumbnail=Url.projectThumbnailUrl+thumbnail;
            myNotification.sendToAllSubscriber("title", "message",thumbnail,token,"project",projectID );
        }
    }
}
