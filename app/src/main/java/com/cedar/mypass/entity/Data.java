package com.cedar.mypass.entity;


import java.sql.Date;

public class Data {
    private int id;
    private String Softname;
    private String Username;
    private String Password;
    private int userid;
    private Date date;

    public Data(int id,String softname,String username,String password,int userid){
        this.id = id;
        this.Softname = softname;
        this.Username = username;
        this.Password = password;
        this.userid = userid;
    }

    public String getSoftname() {
        return Softname;
    }

    public void setSoftname(String softname) {
        Softname = softname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }
}
