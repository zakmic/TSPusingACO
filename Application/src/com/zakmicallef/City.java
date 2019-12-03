package com.zakmicallef;

public class City {

    private int cityId;
    private Point location;

    City() {
    }

    public City(int cityId, Point location) {
        this.cityId = cityId;
        this.location = location;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
