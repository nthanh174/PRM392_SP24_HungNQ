package com.example.shopf.Models;

import java.sql.Date;

public class RegisterDTO {
    private String email;
    private String username;
    private String password;
    private String phone;
    private Date dob;
    private int roleId;

    public RegisterDTO() {
    }

    public RegisterDTO(String email, String username, String password, String phone, Date dob, int roleId) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.dob = dob;
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
