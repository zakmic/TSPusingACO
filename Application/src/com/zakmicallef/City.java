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

    int getCityId() {
        return cityId;
    }

    void setCityId(int cityId) {
        this.cityId = cityId;
    }

    Point getLocation() {
        return location;
    }

    void setLocation(Point location) {
        this.location = location;
    }

    static double distanceTwoCities(Point a, Point b) {
        double xa = a.getX();
        double xb = b.getX();
        double ya = a.getY();
        double yb = b.getY();
        double distance;

        distance = Math.sqrt((xb - xa) * (xb - xa) + (yb - ya) * (yb - ya));

        return distance;
    }
}
