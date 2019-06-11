package com.century21.apexproperty.model.request;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

public class SignIn {
    @NotEmpty
    @NotNull
    @NotBlank
    @Email
    @ApiModelProperty(example = "darong.vann@zillennium.com")
    private String email;
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min = 8)
    @ApiModelProperty(example = "12345678")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "SignIn{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
