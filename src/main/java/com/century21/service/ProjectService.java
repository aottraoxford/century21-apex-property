package com.century21.service;

import com.century21.model.Pagination;
import com.century21.repository.ProjectRepo;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public interface ProjectService {
    ProjectRepo.Project insertProject(ProjectRepo.ProjectRequest project);

    Map<String,Object> uploadProjectImage(MultipartFile thumbnail, MultipartFile[] galleries, int projectID);

    ProjectRepo.Project projectDetail(int projectID, Principal principal);

    List<ProjectRepo.ProjectListingResponse> projects(String title,int cid, int pid, String status, Pagination pagination);

    ProjectRepo.Project deleteImage(int projectID, String galleryName);

    List<ProjectRepo.CountryForWeb> projectsFroWeb(int page, int limit) ;

    ProjectRepo.Project updateProject(ProjectRepo.ProjectRequest projectRequest);

    List<ProjectRepo.ProjectListingResponse> filterProject(ProjectRepo.FilterRequest filterRequest,Pagination pagination);
}
