package com.cedar.mypass.entity;

public class User {
    private int id;
    private String username;
    private String password;

    public User(int id,String name,String pass){
        this.id = id;
        this.username = name;
        this.password = pass;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
