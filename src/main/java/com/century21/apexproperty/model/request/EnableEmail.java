package com.century21.apexproperty.model.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

public class EnableEmail {
    @Email
    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(position = 1)
    private String email;

    @ApiModelProperty(position = 2)
    private int code;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
