package com.century21.century21cambodia.repository.api_projects;

import com.century21.century21cambodia.model.Pagination;

import java.util.List;

public class ProjectTypeForWeb {
    private int id;
    private String type;
    private List<Project> projectList;
    private Pagination pagination;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "ProjectTypeForWeb{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", projectList=" + projectList +
                ", pagination=" + pagination +
                '}';
    }
}
