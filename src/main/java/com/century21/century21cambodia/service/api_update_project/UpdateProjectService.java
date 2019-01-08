package com.century21.century21cambodia.service.api_update_project;

import com.century21.century21cambodia.repository.api_update_project.UpdateProj;
import org.apache.ibatis.annotations.Param;

public interface UpdateProjectService {
    void updateProject(UpdateProj updateProj);
}
