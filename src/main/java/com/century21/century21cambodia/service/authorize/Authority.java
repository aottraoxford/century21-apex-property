package com.century21.century21cambodia.service.authorize;


import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return getRole();
    }

    @Override
    public String toString() {
        return "Authority{" +
                "role='" + role + '\'' +
                '}';
    }
}
