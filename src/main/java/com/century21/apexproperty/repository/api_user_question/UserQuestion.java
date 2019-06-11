package com.century21.apexproperty.repository.api_user_question;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserQuestion {
    @ApiModelProperty(example = "devit")
    private String name;
    @ApiModelProperty(example = "+855|17123876")
    private String phone;
    @ApiModelProperty(example = "user@gmail.com")
    private String email;
    @ApiModelProperty(example = "cambodia")
    private String country;
    @ApiModelProperty(example = "Mean song sa nov.")
    private String issue;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }
}
