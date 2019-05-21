package com.century21.apexproperty.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
//import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.*;

public class SignUp {
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min=6)
    //@ApiModelProperty(position = 1)
    @JsonProperty("first_name")
    private String firstName;
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min=6)
    //@ApiModelProperty(position = 2)
    @JsonProperty("last_name")
    private String lastName;
    @NotBlank
    @NotNull
    @NotEmpty
    //@ApiModelProperty(position = 3)
    private String gender;
    @NotBlank
    @NotNull
    @NotEmpty
    @Email
    //@ApiModelProperty(position = 4)
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @NotBlank
    @NotNull
    @NotEmpty
    @Size(min = 8)
    //@ApiModelProperty(position = 6)
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
