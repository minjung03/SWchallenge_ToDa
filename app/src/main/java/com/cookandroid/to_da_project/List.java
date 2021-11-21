package com.cookandroid.to_da_project;

import android.app.Application;

public class List {

    public String userid;
    public String list_value;
    public String list_chk;
    boolean checked;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getList_value() {
        return list_value;
    }

    public void setList_value(String list_value) {
        this.list_value = list_value;
    }

    public String getList_chk() {
        return list_chk;
    }

    public void setList_chk(String list_chk) {
        this.list_chk = list_chk;
    }

    public boolean isCheked(){
        return checked;
    }

    public void setCheked(boolean checked){
        this.checked =  checked;
    }

}