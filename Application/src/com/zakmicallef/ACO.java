package com.zakmicallef;

import java.util.*;
import java.util.stream.DoubleStream;

class ACO {

    private double[][] distanceMatrix;
    private double[][] pheromoneMatrix;


    private static final double q_NOT = 0.9; //Probability Trigger
    private static final double delta = 0.1; //evaporation rate of local pheromone
    private static final double rho = 0.1; //evaporation rate of global pheromone
    private static final double beta = 4.0; //Trigger Importance
    private static final double alpha = 2.0; //Pheromone Importance

    private double nearestNeighbour;
    private int citiesAmount; // Number of Cities in TSP
    private int antsAmount = 10; // Number of Ants


    void AntColonyOptimization(ArrayList<City> cities) {

        int iterations = 100;
        citiesAmount = cities.size();

        if (citiesAmount < 75){
            antsAmount = citiesAmount;
        }

        //Initialize Distance matrix and Pheromone Levels between the cities
        Ant[] ants = new Ant[antsAmount];
        distanceMatrix(cities);
        nearestNeighbour = nearestNeighbor(citiesAmount);


        for (int i = 0; i < iterations; i++) {

            //Generate the ants
            for (int j = 0; j < antsAmount; j++) {
                //new ant with random initial city
                Ant ant = new Ant(new Random().nextInt(citiesAmount));
                ants[j] = ant;
            }

            //For Every City
            for (int j = 0; j < citiesAmount; j++) {
                for (Ant ant : ants) {
                    //Moves current ant to the next city
                    moveAnt(ant);
                }
            }

            //Preform Pheromone Update on Edge between end city and initial city
            for (Ant ant : ants) {
                localPheromoneUpdate(ant.getPath().get(cities.size() - 1), ant.getPath().get(0));
                ant.setPathDistance(calcPathDistance(ant.getPath()));
            }


            //Preform Global Pheromone Update
            globalPheromoneUpdate(bestAnt(ants));


        }

        Ant bestAnt = bestAnt(ants);
        System.out.println(bestAnt.getPathString());
        System.out.println("Total Path Distance:\t" + bestAnt.getPathDistance());
    }

    private int calcPathDistance(ArrayList<Integer> path) {
        int pathDistance = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            pathDistance += distanceMatrix[path.get(i)][path.get(i + 1)];
        }

        pathDistance += distanceMatrix[path.get(path.size() - 1)][path.get(0)];

        return pathDistance;
    }


    private void moveAnt(Ant ant) {
        //Get the next city that ant will travel too
        int nextCityID = nextCity(ant);

        //Preform Pheromone update to next vertex in path.
        localPheromoneUpdate(ant.getCurrentCity(), nextCityID);

        //Update this ant's details
        ant.setCurrentCity(nextCityID);
        ant.getPath().add(nextCityID);
        ant.getVisitedCities().add(nextCityID);
    }

    private void globalPheromoneUpdate(Ant bestAnt) {

        for (int i = 0; i < bestAnt.getPath().size() - 1; i++) {
            int cityA = bestAnt.getPath().get(i);
            int cityB = bestAnt.getPath().get(i + 1);

            pheromoneMatrix[cityA][cityB] *= (1 - rho);
            pheromoneMatrix[cityA][cityB] += rho * (1 / bestAnt.getPathDistance());
            symmetric(cityA, cityB);
        }

        int cityA = bestAnt.getPath().get(bestAnt.getPath().size() - 1);
        int cityB = bestAnt.getPath().get(0);

        pheromoneMatrix[cityA][cityB] *= (1 - rho);
        pheromoneMatrix[cityA][cityB] += rho * (1 / bestAnt.getPathDistance());
        symmetric(cityA, cityB);

//        for (int i = 0; i < citiesAmount - 1; i++) {
//            for (int j = 0; j < citiesAmount - 1; j++) {
//                pheromoneMatrix[i][j] += delta * (1 / bestAnt.getPathDistance());
//                symmetric(i, j);
//            }
//        }
    }

    private static Ant bestAnt(Ant[] ants) {

        double leastDistance = ants[0].getPathDistance();
        Ant bestAnt = ants[0];

        for (Ant ant : ants) {
            if (ant.getPathDistance() < leastDistance) {
                leastDistance = ant.getPathDistance();
                bestAnt = ant;
            }
        }

        return bestAnt;
    }

    private void localPheromoneUpdate(int currentCity, int nextCity) {

        pheromoneMatrix[currentCity][nextCity] = (1 - delta) * (pheromoneMatrix[currentCity][nextCity])
                + (delta * (1 / (citiesAmount * nearestNeighbour)));
        symmetric(currentCity, nextCity);
    }


    private int nextCity(Ant ant) {

        int nextCityID = 0;
        double[] probability = new double[citiesAmount];

        for (int i = 0; i < citiesAmount; i++) {
            if (!ant.getVisitedCities().contains(i)) {
                double tau = pheromoneMatrix[ant.getCurrentCity()][i];
                double eta = 1 / distanceMatrix[ant.getCurrentCity()][i];
                probability[i] = Math.pow(tau, alpha) * Math.pow(eta, beta);
            }
        }

        if (new Random().nextDouble() < q_NOT) {
            nextCityID = bestCity(probability);
        } else {
            double[] probabilityAlt = new double[citiesAmount];

            double totalProbability = DoubleStream.of(probability).sum();

            probabilityAlt[0] = probability[0] / totalProbability;

            for (int i = 1; i < probabilityAlt.length; i++) {
                probabilityAlt[i] = probabilityAlt[i - 1] + (probability[i] / totalProbability);
            }

            //Roulette Wheel Selection
            while (new Random().nextDouble() > probabilityAlt[nextCityID]) {
                nextCityID++;
            }
        }
        return nextCityID;
    }


    private static int bestCity(double[] probability) {

        // choose the city with the highest 'score' as the next city the ant will visit
        double maxProbability = probability[0];
        int bestCity = 0;

        for (int i = 1; i < probability.length; i++) {
            if (probability[i] > maxProbability) {
                maxProbability = probability[i];
                bestCity = i;
            }
        }

        return bestCity;
    }

    private double nearestNeighbor(int pathDistance) {

        int startingCity = new Random().nextInt(citiesAmount);

        Ant ant = new Ant(startingCity);

        Set<Integer> unvisited = new HashSet<>();

        for (int i = 0; i < pathDistance; i++) {
            unvisited.add(i);
        }

        unvisited.remove(startingCity);

        int currentCity = startingCity;

        for (int i = 1; i < pathDistance; i++) {

            int nearestCity = unvisited.iterator().next();

            for (Integer city : unvisited) {
                if (distanceMatrix[currentCity][city] < distanceMatrix[currentCity][nearestCity]) {
                    nearestCity = city;
                }
            }

            ant.getPath().add(nearestCity);
            currentCity = nearestCity;
            unvisited.remove(nearestCity);
        }

        return calcPathDistance(ant.getPath());
    }

    private void distanceMatrix(ArrayList<City> cities) {
        distanceMatrix = new double[citiesAmount][citiesAmount];
        pheromoneMatrix = new double[citiesAmount][citiesAmount];


        for (int i = 0; i < citiesAmount; i++) {
            for (int j = 0; j < citiesAmount; j++) {
                distanceMatrix[i][j] = City.distanceTwoCities(cities.get(i).getLocation(), cities.get(j).getLocation());
                distanceMatrix[j][i] = distanceMatrix[i][j];
                pheromoneMatrix[i][j] = 0.0001;
                symmetric(i, j);
            }
        }
    }

    private void symmetric(int a, int b) {
        pheromoneMatrix[b][a] = pheromoneMatrix[a][b];
    }
}
