package com.century21.apexproperty.repository.api_project_statistic;

public class Type{
    private String type;
    private int total;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Type{" +
                "type='" + type + '\'' +
                ", total=" + total +
                '}';
    }
}
