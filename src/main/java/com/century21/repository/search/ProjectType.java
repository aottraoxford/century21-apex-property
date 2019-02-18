package com.century21.repository.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectType {
    private Integer id;
    @JsonProperty("type")
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
