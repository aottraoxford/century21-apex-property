package com.century21.century21cambodia.model.request;

import com.century21.century21cambodia.exception.CustomRuntimeException;
import com.century21.century21cambodia.util.Url;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SocialSignIn {
    private String email;
    @JsonProperty("social_id")
    private String socialId;
    @JsonProperty("social_type")
    private String socialType;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String gender;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("photo")
    private String photo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSocialId() {
        if(socialId==null)
            throw new CustomRuntimeException(400,"social_id can not be empty or null");
        return socialId;
    }

    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }

    public String getSocialType() {
        return socialType;
    }

    public void setSocialType(String socialType) {
        this.socialType = socialType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

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

    public String getPhoto() {

        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "SocialSignIn{" +
                "email='" + email + '\'' +
                ", socialId='" + socialId + '\'' +
                ", socialType='" + socialType + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
