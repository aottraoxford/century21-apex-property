package com.century21.apexproperty.service;

import com.century21.apexproperty.repository.UserRepo;

import java.security.Principal;
import java.util.List;

public interface UserService {
    void sendMail(String email);
    void changePassword(UserRepo.ChangePassword changePassword);
    void verification(int code);
    void assignRole(int userID,String roleType);
    void addAgent(int userID,Principal principal);
    List<UserRepo.User> agents(String name,Principal principal);
}
