package com.cookandroid.to_da_project;


public class User {
     String nicname;
     String userID;
     String userPW;

    public User(String nicname, String userID, String userPW) {
        this.nicname = nicname;
        this.userID = userID;
        this.userPW = userPW;
    }

    public String getNicname() {
        return nicname;
    }

    public void setNicname(String nicname) {
        this.nicname = nicname;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserPW() {
        return userPW;
    }

    public void setUserPW(String userPW) {
        this.userPW = userPW;
    }
}
