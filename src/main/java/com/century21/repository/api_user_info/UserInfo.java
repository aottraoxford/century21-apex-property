package com.century21.repository.api_user_info;

import com.century21.util.Url;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfo {
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String gender;
    private String email;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("photo")
    private String image;
    private String swapEmail;

    public String getImage() {
        String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
        Pattern pattern;
        Matcher matcher;
        pattern=Pattern.compile(EMAIL_REGEX,Pattern.CASE_INSENSITIVE);
        matcher=pattern.matcher(swapEmail);
        if(matcher.matches()) {
            if (image != null)
                return Url.userImageUrl + image;
        }
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        swapEmail=email;
        if(email.contains("|")) {
            String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
            Pattern pattern;
            Matcher matcher;
            pattern=Pattern.compile(EMAIL_REGEX,Pattern.CASE_INSENSITIVE);
            email=email.split("\\|")[0];
            matcher=pattern.matcher(email);
            if(!matcher.matches()) return null;
        }
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
}