package ru.maxlt.carbase;

public class CarDetail {
    private String beaten_price_msrp, capacity, car_id, convenience, drive_type, engine, exterior_color, interior_color, mpg, price_msrp, production_link, production_video_name, test_drive_link, test_drive_video_name, transmission;

    public CarDetail(String beaten_price_msrp, String capacity, String car_id, String convenience, String drive_type, String engine, String exterior_color, String interior_color, String mpg, String price_msrp, String production_link, String production_video_name, String test_drive_link, String test_drive_video_name, String transmission) {
        this.beaten_price_msrp = beaten_price_msrp;
        this.capacity = capacity;
        this.car_id = car_id;
        this.convenience = convenience;
        this.drive_type = drive_type;
        this.engine = engine;
        this.exterior_color = exterior_color;
        this.interior_color = interior_color;
        this.mpg = mpg;
        this.price_msrp = price_msrp;
        this.production_link = production_link;
        this.production_video_name = production_video_name;
        this.test_drive_link = test_drive_link;
        this.test_drive_video_name = test_drive_video_name;
        this.transmission = transmission;
    }

    public CarDetail() {
    }

    public String getBeaten_price_msrp() {
        return beaten_price_msrp;
    }

    public void setBeaten_price_msrp(String beaten_price_msrp) {
        this.beaten_price_msrp = beaten_price_msrp;
    }

    public String getCapacity() {
        return capacity;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public String getCar_id() {
        return car_id;
    }

    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }

    public String getConvenience() {
        return convenience;
    }

    public void setConvenience(String convenience) {
        this.convenience = convenience;
    }

    public String getDrive_type() {
        return drive_type;
    }

    public void setDrive_type(String drive_type) {
        this.drive_type = drive_type;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getExterior_color() {
        return exterior_color;
    }

    public void setExterior_color(String exterior_color) {
        this.exterior_color = exterior_color;
    }

    public String getInterior_color() {
        return interior_color;
    }

    public void setInterior_color(String interior_color) {
        this.interior_color = interior_color;
    }

    public String getMpg() {
        return mpg;
    }

    public void setMpg(String mpg) {
        this.mpg = mpg;
    }

    public String getPrice_msrp() {
        return price_msrp;
    }

    public void setPrice_msrp(String price_msrp) {
        this.price_msrp = price_msrp;
    }

    public String getProduction_link() {
        return production_link;
    }

    public void setProduction_link(String production_link) {
        this.production_link = production_link;
    }

    public String getProduction_video_name() {
        return production_video_name;
    }

    public void setProduction_video_name(String production_video_name) {
        this.production_video_name = production_video_name;
    }

    public String getTest_drive_link() {
        return test_drive_link;
    }

    public void setTest_drive_link(String test_drive_link) {
        this.test_drive_link = test_drive_link;
    }

    public String getTest_drive_video_name() {
        return test_drive_video_name;
    }

    public void setTest_drive_video_name(String test_drive_video_name) {
        this.test_drive_video_name = test_drive_video_name;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }
}
