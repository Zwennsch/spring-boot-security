package com.svenschroeder.security_example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class MyUser {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String password;
    private String Role; // eg. USER,ADMIN

    public String getRole() {
        return Role;
    }
    public void setRole(String role) {
        Role = role;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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

    

    
    
}
