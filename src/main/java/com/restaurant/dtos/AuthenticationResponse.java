package com.restaurant.dtos;

import com.restaurant.enums.UserRole;

public class AuthenticationResponse {

    private String jwt;
    private UserRole userRole;

    private Long userid;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
