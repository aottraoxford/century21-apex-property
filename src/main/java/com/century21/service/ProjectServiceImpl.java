package com.century21.service;

import com.century21.configuration.upload.FileUploadProperty;
import com.century21.configuration.upload.FileUploadService;
import com.century21.exception.CustomRuntimeException;
import com.century21.model.ID;
import com.century21.model.Pagination;
import com.century21.repository.ProjectRepo;
import com.century21.repository.UserRepo;
import com.century21.util.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private FileUploadProperty fileUploadProperty;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private UserRepo userRepo;

    @Override
    public ProjectRepo.Project insertProject(ProjectRepo.ProjectRequest projectRequest) {
        if (projectRequest.getProjectTypeID() == 2) {
            if (projectRequest.getProjectIntroductions() != null || projectRequest.getProjectIntroductions().size() > 0)
                throw new CustomRuntimeException(400, "Condo project can be only TOWER_TYPE");
        }

        ID id = new ID();
        projectRepo.insertProject(id, projectRequest);
        int projectID = id.getId();

        if (projectRequest.getProjectTypeID() == 2) {
            for (int i = 0; i < projectRequest.getTowerTypes().size(); i++) {
                projectRepo.insertTowerType(projectRequest.getTowerTypes().get(i).getType(), projectID);
            }
        } else {
            if (projectRequest.getPropertyTypes() != null) {
                for (int i = 0; i < projectRequest.getPropertyTypes().size(); i++) {
                    projectRepo.insertPropertyType(projectRequest.getPropertyTypes().get(i), projectID);
                }
            }
        }
        if (projectRequest.getProjectIntroductions() != null) {
            for (int i = 0; i < projectRequest.getProjectIntroductions().size(); i++)
                projectRepo.insertProjectIntro(projectRequest.getProjectIntroductions().get(i), projectID);
        }
        ProjectRepo.Project project = projectRepo.findOneProject(projectID);
        return project;
    }

    @Override
    public ProjectRepo.Project updateProject(ProjectRepo.ProjectRequest projectRequest) {
        Collection<Integer> idFromRequest = new ArrayList<>();
        Collection<Integer> idFromDB;

        Integer countryID = projectRepo.findCountryIDByName(projectRequest.getCountry());
        if(countryID==null) throw new CustomRuntimeException(404,"Country not exist.");
        Integer projectTypeID = projectRepo.findProjectTypeIDByName(projectRequest.getProjectType());
        if(projectTypeID==null) throw new CustomRuntimeException(404,"Project type not exist.");

        projectRequest.setCountryID(countryID);
        projectRequest.setProjectTypeID(projectTypeID);

        projectRepo.updateProject(projectRequest);

        if(projectRequest.getProjectIntroductions()!=null || projectRequest.getProjectIntroductions().size()>0){
            idFromDB=projectRepo.findAllProjectIntroID(projectRequest.getId());
            for(int i=0;i<projectRequest.getProjectIntroductions().size();i++){
                ProjectRepo.ProjectIntroduction projectIntroduction=projectRequest.getProjectIntroductions().get(i);
                if(projectRepo.updateProjectIntro(projectIntroduction,projectRequest.getId())<1)
                    projectRepo.insertProjectIntro(projectIntroduction,projectRequest.getId());
                idFromRequest.add(projectIntroduction.getId());
            }
            idFromDB.addAll(idFromRequest);
            idFromDB.removeAll(idFromRequest);
            for (int i = 0; i < idFromDB.size(); i++)
                projectRepo.removeProjectIntro(((List<Integer>) idFromDB).get(i),projectRequest.getId());
        }

        if (projectRequest.getProjectTypeID() == 2 && projectRequest.getTowerTypes() != null) {
            idFromDB = projectRepo.findAllTowerTypeID(projectRequest.getId());
            for (int i = 0; i < projectRequest.getTowerTypes().size(); i++) {
                ProjectRepo.TowerType towerType = projectRequest.getTowerTypes().get(i);
                if (projectRepo.updateTowerType(towerType,projectRequest.getId()) < 1)
                    projectRepo.insertTowerType(towerType.getType(), projectRequest.getId());
                idFromRequest.add(towerType.getId());
            }
            idFromDB.addAll(idFromRequest);
            idFromDB.removeAll(idFromRequest);
            for (int i = 0; i < idFromDB.size(); i++)
                projectRepo.removeTowerType(((List<Integer>) idFromDB).get(i),projectRequest.getId());
        } else {
            if (projectRequest.getPropertyTypes() != null) {
                idFromDB = projectRepo.findAllPropertyTypeID(projectRequest.getId());
                for (int i = 0; i < projectRequest.getPropertyTypes().size(); i++) {
                    ProjectRepo.PropertyType propertyType = projectRequest.getPropertyTypes().get(i);
                    if (projectRepo.updatePropertyType(propertyType,projectRequest.getId()) < 1) {
                        projectRepo.insertPropertyType(propertyType, projectRequest.getId());
                    }
                    idFromRequest.add(propertyType.getId());
                }
                idFromDB.addAll(idFromRequest);
                idFromDB.removeAll(idFromRequest);
                for(int i=0;i<idFromDB.size();i++)
                    projectRepo.removePropertyType(((List<Integer>) idFromDB).get(i),projectRequest.getId());
            }
        }
        return projectRepo.findOneProject(projectRequest.getId());
    }

    @Override
    public Map<String, Object> uploadProjectImage(MultipartFile thumbnail, MultipartFile[] galleries, int projectID) {
        String aThumbnail = projectRepo.findOneThumbnail(projectID);
        String nameThumbnail = "";
        if (thumbnail != null) {
            if (aThumbnail != null) fileUploadService.removeImage(aThumbnail, fileUploadProperty.getProjectThumbnail());
            nameThumbnail = Url.projectThumbnailUrl + fileUploadService.storeImage(thumbnail, fileUploadProperty.getProjectThumbnail());
        }
        List<String> nameGalleries = new ArrayList<>();
        if (galleries != null || galleries.length > 0) {
            nameGalleries = fileUploadService.storeImages(galleries, fileUploadProperty.getProjectGallery());
            for (int i = 0; i < nameGalleries.size(); i++) {
                projectRepo.insertGallery(nameGalleries.get(i), projectID);
                nameGalleries.set(i, Url.projectGalleryUrl + nameGalleries.get(i));
            }
        }
        Map<String, Object> map = new HashMap();
        map.put("thumbnail", nameThumbnail);
        map.put("galleries", nameGalleries);
        return map;
    }

    @Override
    public ProjectRepo.Project projectDetail(int projectID, Principal principal) {
        ProjectRepo.Project project = projectRepo.findOneProject(projectID);
        if (project == null) throw new CustomRuntimeException(404, "ZERO RESULT");
        Integer userID = userRepo.findUserIDByEmail(principal.getName());
        if (userID != null) {
            Integer favID = projectRepo.favorite(projectID, userID);
            if (favID != null) project.setFavorite(true);
        }
        return project;
    }

    @Override
    public List<ProjectRepo.ProjectListingReponse> projects(String title, int cid, int pid, String status, Pagination pagination) {
        if (title != null) {
            title = title.trim().replaceAll(" ", "%");
        }
        List<ProjectRepo.ProjectListingReponse> projects = projectRepo.findAllProject(title, cid, pid, status, pagination.getLimit(), pagination.getOffset());
        if (projects == null || projects.size() < 1) throw new CustomRuntimeException(404, "ZERO_RESULT");
        pagination.setTotalItem(projectRepo.findAllProjectCount(title, cid, pid, status));
        return projects;
    }

    @Override
    public ProjectRepo.Project deleteImage(int projectID, String galleryName) {
//        if (thumbnail != null) {
//            String oldThumbnail = projectRepo.findOneThumbnail(projectID);
//            if (oldThumbnail != null)
//                fileUploadService.removeImage(oldThumbnail, fileUploadProperty.getProjectThumbnail());
//            String newThumbnail = fileUploadService.storeImage(thumbnail, fileUploadProperty.getProjectThumbnail());
//            projectRepo.updateThumbnail(newThumbnail, projectID);
//        }
        if (galleryName != null) {
            fileUploadService.removeImage(galleryName, fileUploadProperty.getProjectGallery());
            projectRepo.removeOneGallery(galleryName);
        }
//        if (galleries.length > 0) {
//            for (int i = 0; i < galleries.length; i++) {
//                fileUploadService.removeImage(galleries[i].getOriginalFilename(), fileUploadProperty.getProjectGallery());
//                projectRepo.removeOneGallery(galleries[i].getOriginalFilename());
//            }
//        }
        return projectRepo.findOneProject(projectID);
    }

    @Override
    public List<ProjectRepo.CountryForWeb> projectsFroWeb(int page, int limit) {
        List<ProjectRepo.CountryForWeb> countryForWeb = projectRepo.getCountryForWeb();
        for (int i = 0; i < countryForWeb.size(); i++) {
            for (int j = 0; j < countryForWeb.get(i).getProjectTypeForWebList().size(); j++) {
                countryForWeb.get(i).getProjectTypeForWebList().get(j).setProjectList(new ArrayList<>());
                countryForWeb.get(i).getProjectTypeForWebList().get(j).setPagination(new Pagination(1, 10));
                if (j + 1 == countryForWeb.get(i).getProjectTypeForWebList().size()) {
                    ProjectRepo.ProjectTypeForWeb projectTypeForWeb = new ProjectRepo.ProjectTypeForWeb();
                    projectTypeForWeb.setProjectList(new ArrayList<>());
                    projectTypeForWeb.setPagination(new Pagination(1, 10));
                    projectTypeForWeb.setId(0);
                    projectTypeForWeb.setType("all");
                    countryForWeb.get(i).getProjectTypeForWebList().add(0, projectTypeForWeb);
                    break;
                }
            }
        }
        return countryForWeb;

    }
}
