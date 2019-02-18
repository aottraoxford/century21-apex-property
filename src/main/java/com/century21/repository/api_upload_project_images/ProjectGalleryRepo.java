package com.century21.repository.api_upload_project_images;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectGalleryRepo {

    @Select("SELECT thumbnail " +
            "FROM project " +
            "WHERE id=#{projectID}")
    String findThumbnail(int projectID);

    @Insert("INSERT INTO project_gallery(url,type,project_id) " +
            "VALUES(#{gall},'image',#{pid})")
    int saveGallery(@Param("gall") String galleries, @Param("pid") int projectID);

    @Update("UPDATE project SET thumbnail = #{thumbnail} " +
            "WHERE id=#{projectID}")
    int saveThumbnail(@Param("thumbnail") String thumbnail, @Param("projectID") int projectID);

}
