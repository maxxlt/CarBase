package ru.maxlt.carbase;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String full_name, user_id;
    private Map<String,UserFavorites> favorites;

    public User() {
    }

    public User(String full_name, String user_id, Map<String,UserFavorites> favorites) {
        this.full_name = full_name;
        this.user_id = user_id;
        this.favorites = favorites;
    }

    public User(String full_name, String user_id) {
        this.full_name = full_name;
        this.user_id = user_id;
    }

    public Map<String, UserFavorites> getFavorites() {
        return favorites;
    }

    public void setFavorites(Map<String, UserFavorites> favorites) {
        this.favorites = favorites;
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
