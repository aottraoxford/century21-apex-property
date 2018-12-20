package com.century21.century21cambodia.model.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

public class EnableEmail {
    @Email
    @NotBlank
    @NotEmpty
    @NotNull
    @ApiModelProperty(position = 1)
    private String email;

    @Max(value = 9999)
    @Min(value = 1000)
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
