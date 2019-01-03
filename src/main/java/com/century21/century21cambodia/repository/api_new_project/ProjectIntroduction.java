package com.century21.century21cambodia.repository.api_new_project;

public class ProjectIntroduction {
    private String name;
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "ProjectIntroduction{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
