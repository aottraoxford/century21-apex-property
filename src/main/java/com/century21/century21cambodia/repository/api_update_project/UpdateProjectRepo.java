package com.century21.century21cambodia.repository.api_update_project;

import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.mapping.StatementType;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateProjectRepo {

    @Select("SELECT id " +
            "FROM country " +
            "WHERE name ILIKE #{name}")
    Integer findCountryID(@Param("name")String name);

    @Select("SELECT id " +
            "FROM project_type " +
            "WHERE name ILIKE #{projectType}")
    Integer findProjectTypeID(@Param("projectType")String projectType);

    @Update("UPDATE project_intro SET name = #{name},description = #{description} " +
            "WHERE project_id=#{projectID} AND name ILIKE #{name}")
    int updateIntroduction(@Param("projectID") int projectID,@Param("name")String name,@Param("description")String description);

    @Select(value= "{CALL update_project(#{uPro.projectID},#{uPro.title},#{cid},#{uPro.description},#{uPro.addressOne},#{uPro.addressTwo},#{uPro.minPrice},#{uPro.maxPrice},#{uPro.averageAnnualRentFrom},#{uPro.averageAnnualRentTo},#{uPro.downPayment},#{pid},#{uPro.status})}")
    @Options(statementType = StatementType.CALLABLE)
    int updateProject(@Param("uPro")UpdateProj updateProj,@Param("cid")int cid,@Param("pid")int pid);

}
