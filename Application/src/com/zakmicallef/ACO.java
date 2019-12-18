package com.zakmicallef;

import java.util.ArrayList;

public class ACO {
    private double[][] distanceMatrix;
    private double[][] pheromoneMatrix;
    private int antsAmount = 10;


    public void ACO(ArrayList<City> cities) {

        //Initialize the Ants using Ant array
        Ant[] ants = new Ant[antsAmount];

        //Initialize Distance matrix and Phermone Levels between the cities
        distanceMatrix(cities);

        //Init Ants List
        int iterations = 100;
        for (int i = 0; i < iterations; i++) {

            //For Every Ant
            for (int j = 0; j < antsAmount; j++) {
                //Set Ant Path
            }

            //For Every City 
            for (int j = 0; j < cities.size(); j++) {
                for (Ant ant : ants) {
                    //Moves current ant to the next city
                    moveAnt(ant, cities);
                }
            }


        }


    }

    private void moveAnt(Ant ant, ArrayList<City> cities) {
        int nextCityID = nextCity(cities, ant);

        ant.setCurrentCity(nextCityID);
        ant.getPath().add(nextCityID);
        ant.getVisitedCities().add(nextCityID);

        localPheromoneUpdate(ant.getCurrentCity(), nextCityID);
    }

    private void localPheromoneUpdate(int currentCity, int nextCityID) {
    }

    private int nextCity(ArrayList<City> cities, Ant ant) {

        return 0;
    }

    private void distanceMatrix(ArrayList<City> cities) {
        distanceMatrix = new double[cities.size()][cities.size()];

        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                distanceMatrix[i][j] = City.distanceTwoCities(cities.get(i).getLocation(), cities.get(j).getLocation());
                distanceMatrix[j][i] = distanceMatrix[i][j];
                pheromoneMatrix[i][j] = 0.000000001;
                pheromoneMatrix[j][i] = 0.000000001;
            }
        }
    }


}
