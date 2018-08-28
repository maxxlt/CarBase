package ru.maxlt.carbase;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String full_name, user_id;

    public User() {
    }

    public User(String full_name, String user_id) {
        this.full_name = full_name;
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
