package com.century21.century21cambodia.repository.api_project_statistic;

public class Event {
    private int total;
    private int totalEnable;
    private int totalDisable;

    public int getTotal() {
        return totalEnable+totalDisable;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalEnable() {
        return totalEnable;
    }

    public void setTotalEnable(int totalEnable) {
        this.totalEnable = totalEnable;
    }

    public int getTotalDisable() {
        return totalDisable;
    }

    public void setTotalDisable(int totalDisable) {
        this.totalDisable = totalDisable;
    }
}
