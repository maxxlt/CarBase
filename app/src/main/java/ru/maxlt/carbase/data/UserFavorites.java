package ru.maxlt.carbase.data;

public class UserFavorites {
    private String car_id;

    public UserFavorites() {
    }

    public UserFavorites(String car_id) {
        this.car_id = car_id;
    }

    public String getCar_id() {

        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }
}
