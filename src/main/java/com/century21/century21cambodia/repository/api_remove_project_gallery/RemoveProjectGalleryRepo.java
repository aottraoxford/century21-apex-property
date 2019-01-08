package com.century21.century21cambodia.repository.api_remove_project_gallery;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RemoveProjectGalleryRepo {
    @Delete("DELETE FROM project_gallery " +
            "WHERE url = #{imageName}")
    int removeGallery(@Param("imageName")String imageName);
}
