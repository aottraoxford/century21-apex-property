package com.century21.repository.api_projects;

public class ProjectType {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ProjectType{" +
                "name='" + name + '\'' +
                '}';
    }
}