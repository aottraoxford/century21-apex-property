package com.century21.century21cambodia.repository.api_visible_project;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface VisibleProjectRepo {
    @Update("UPDATE project set isdisplay = #{status} " +
            "WHERE id=#{projectID}")
    int visibleProject(@Param("status") boolean status, @Param("projectID") int projectID);
}
