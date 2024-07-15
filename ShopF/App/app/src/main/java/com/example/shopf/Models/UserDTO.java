package com.example.shopf.Models;

import java.sql.Date;

public class UserDTO {
    private int userId;
    private String email;
    private String username;
    private String password;
    private String phone;
    private int roleId;
    private Date dob;
    private Date createdAt;
    private Date updatedAt;

    public UserDTO() {
    }

    public UserDTO(int userId, String email, String username, String password, String phone, int roleId, Date dob, Date createdAt, Date updatedAt) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.roleId = roleId;
        this.dob = dob;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
