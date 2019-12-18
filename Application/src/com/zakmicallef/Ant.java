package com.zakmicallef;

import java.util.ArrayList;

public class Ant {

    private int currentCity;
    private int pathDistance;
    private ArrayList<Integer> visitedCities;
    private ArrayList<Integer> path;


    public Ant(int initialCity) {
        this.currentCity = initialCity;
        this.path = new ArrayList<Integer>();
        path.add(initialCity);
        this.pathDistance = 0;
//        this.visitedCities = visitedCities;
    }

    public int getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(int currentCity) {
        this.currentCity = currentCity;
    }

    public int getPathDistance() {
        return pathDistance;
    }

    public void setPathDistance(int pathDistance) {
        this.pathDistance = pathDistance;
    }

    public ArrayList<Integer> getVisitedCities() {
        return visitedCities;
    }

    public void setVisitedCities(ArrayList<Integer> visitedCities) {
        this.visitedCities = visitedCities;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }
}
