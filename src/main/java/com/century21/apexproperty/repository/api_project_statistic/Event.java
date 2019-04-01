package com.century21.apexproperty.repository.api_project_statistic;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Event {
    private int total;
    @JsonProperty("total_enable")
    private int totalEnable;
    @JsonProperty("total_disable")
    private int totalDisable;

    public int getTotal() {
        return totalEnable+totalDisable;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int total() {
        return totalEnable+totalDisable;
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
