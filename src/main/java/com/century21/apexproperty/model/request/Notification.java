package com.century21.apexproperty.model.request;

import io.swagger.annotations.ApiModelProperty;

public class Notification {
    @ApiModelProperty(value = "this is message-notification use for show to clients")
    private String message;

    public Notification(String message) {
        this.message = message;
    }

    public String getMessage() {
        if(message==null ) return "New Project Available.";
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
