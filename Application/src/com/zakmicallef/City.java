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

    public double distanceTwoCities(Point a, Point b) {
        double xa = a.getX();
        double xb = b.getY();
        double ya = a.getY();
        double yb = b.getY();
        double distance;

        distance = Math.sqrt((xb - xa) * (xb - xa) + (yb - ya) * (yb - ya));

        return distance;
    }
}
