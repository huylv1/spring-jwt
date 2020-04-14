package com.example.demo;

import java.util.Date;

public class User {
    private Integer userid;
    private String username;
    private static Integer userCounter = 100;

    public User() {
    }

    public User(String username, String password, Integer usertype) {
        userCounter++;
        this.userid = userCounter;
        this.username = username;
        this.password = password;
        this.usertype = usertype;
    }

    public User(Integer userid, String username) {
        this.userid = userid;
        this.username = username;
    }
    public User(Integer userid, String username, Date updatedDate){
        this(userid, username);
        this.updatedDate = updatedDate;
    }

    private Date updatedDate;

    public User(Integer userid, Integer usertype) {
        this.userid = userid;
        this.usertype = usertype;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private String password;
    /*
     * usertype:
     * 1 - general user
     * 2 - CSR (Customer Service Representative)
     * 3 - admin
     */
    private Integer usertype;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setUsertype(Integer usertype){
        this.usertype = usertype;
    }
    public Integer getUsertype(){
        return this.usertype;
    }

    @Override
    public String toString() {
        return "User [userid=" + userid + ", username=" + username + ", updatedDate=" + updatedDate + ']';
    }
}
