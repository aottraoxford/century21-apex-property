package com.century21.apexproperty.repository.api_project_statistic;

import java.util.List;

public class Country{
    private String name;
    private int total;
    private List<Type> types;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Type> getTypes() {
        return types;
    }

    public void setTypes(List<Type> types) {
        this.types = types;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", total=" + total +
                ", types=" + types +
                '}';
    }
}
