package com.century21.century21cambodia.repository.api_remove_project_gallery;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RemoveProjectGalleryRepo {
    @Delete("DELETE FROM project_gallery " +
            "WHERE url = #{imageName}")
    Integer removeGallery(@Param("imageName")String imageName);

    @Update("UPDATE project SET thumbnail = #{thumbnail} " +
            "WHERE id = #{projectID}")
    Integer removeThumbnail(@Param("projectID")int projectID,@Param("thumbnail")String thumbnail);

    @Select("SELECT count(id) " +
            "FROM project " +
            "WHERE id=#{projectID}")
    Integer checkID(int projectID);

    @Select("SELECT url " +
            "FROM project_gallery " +
            "WHERE type = 'image' AND project_id=#{projectID}")
    List<String> galleries(int projectID);
}
