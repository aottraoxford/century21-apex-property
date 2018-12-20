package com.century21.century21cambodia.repository.projects;

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
