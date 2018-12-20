package com.century21.century21cambodia.service.new_project;

import com.century21.century21cambodia.repository.new_project.Galleries;
import com.century21.century21cambodia.repository.new_project.Project;
import java.util.List;

public interface NewProjectService {
    int createNewProject(Project project);
    void saveGallery(String thumbnail,List<Galleries> galleries, int projectID);
}
