package com.century21.apexproperty.repository.api_visible_project;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface VisibleProjectRepo {
    @Select("SELECT isdisplay " +
            "FROM project " +
            "WHERE id=#{projectID}")
    boolean checkProjectStatus(@Param("projectID") int projectID);

    @Select("SELECT thumbnail " +
            "FROM project " +
            "WHERE id=#{projectID}")
    String thumbnail(@Param("projectID") int projectID);

    @Update("UPDATE project set isdisplay = #{status} " +
            "WHERE id=#{projectID}")
    int visibleProject(@Param("status") boolean status, @Param("projectID") int projectID);
}
