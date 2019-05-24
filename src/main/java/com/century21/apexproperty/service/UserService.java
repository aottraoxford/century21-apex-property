package com.century21.apexproperty.service;

import com.century21.apexproperty.model.Pagination;
import com.century21.apexproperty.repository.UserRepo;

import java.security.Principal;
import java.util.List;

public interface UserService {
    void sendMail(String email);
    void changePassword(UserRepo.ChangePassword changePassword);
    void verification(int code);
    void assignRole(int userID,String roleType,Principal principal);
    List<UserRepo.User> agents(String name, Principal principal, Pagination pagination);
    List<UserRepo.User> findUsers(String name,String role,Pagination pagination);
    List<UserRepo.Contact> findContacts(UserRepo.ContactFilter filter,Pagination pagination,Principal principal);
    List<UserRepo.Question> findQuestions(Pagination pagination);
}
