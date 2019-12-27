package com.zakmicallef;

import java.util.ArrayList;

public class Ant {

    private int currentCity;
    private double pathDistance;
    private ArrayList<Integer> visitedCities;
    private ArrayList<Integer> path;


    public Ant(int initialCity) {
        this.currentCity = initialCity;
        this.path = new ArrayList<Integer>();
        path.add(initialCity);
        this.pathDistance = 0;
        visitedCities = new ArrayList<>();
        visitedCities.add(currentCity);
    }

    public int getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(int currentCity) {
        this.currentCity = currentCity;
    }

    public double getPathDistance() {
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

    // method which returns the path of an Ant as a string
    public String getPathString() {
        StringBuilder string = new StringBuilder();
        for (int city : path) {
            string.append((city + 1) + " ");
        }
        return string.toString();
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }
}
