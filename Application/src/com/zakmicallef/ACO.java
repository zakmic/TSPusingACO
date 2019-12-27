package com.zakmicallef;

import java.util.*;
import java.util.stream.DoubleStream;

class ACO {

    private double[][] distanceMatrix;
    private double[][] pheromoneMatrix;
    private int antsAmount = 10; //Number of Ants


    private static final double q_NOT = 0.9; //Probability Trigger
    private double delta = 0.1; //evaporation rate of local pheromone
    private double ro = 0.1; //evaporation rate of global pheromone
    private static final double beta = 3.0; //Trigger Importance
    private static final double alpha = 2.0; //Pheromone Importance

    private int citiesAmount = 0; // Number of Cities in TSP
    private double Lnn = 0;

    //Initialize the Ants using Ant array
    private Ant[] ants = new Ant[antsAmount];


    void AntColonyOptimization(ArrayList<City> cities) {

        citiesAmount = cities.size();

//        ArrayList<Ant> ants = new ArrayList<Ant>();

        //Initialize Distance matrix and Phermone Levels between the cities
        distanceMatrix(cities);

        //Init Ants List
        int iterations = 100;

        Lnn = nearestNeighbor(citiesAmount);

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
                    moveAnt(ant, cities);
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
        System.out.println("Total Chosen Path Distance:\t" + bestAnt.getPathDistance());

    }

    // Change All
    private double nearestNeighbor(int pathDistance) {
        // declaring the city the Nearest Neighbour Algorithm Ant will start from
        int startingCity = new Random().nextInt(citiesAmount);

        // set the currentCity as the Starting City
        int currentCity = startingCity;
        // declaring an Ant which will store the path followed by the nearest neighbour algorithm
        // initialise the first city in the path as the startingCity
        Ant nn = new Ant(startingCity);

        // Declare a new Set of unvisitedCities, and fill it with all the city IDs
        Set<Integer> unvisitedCities = new HashSet<>();
        for (int i = 0; i < pathDistance; i++) {
            unvisitedCities.add(i);
        }
        // remove the startingCity from the set of unvisitedCities
        unvisitedCities.remove(startingCity);

        // for the length of the path
        for (int i = 1; i < pathDistance; i++) {
            // initially set the closestCity to a random city from the set of unvisitedCities
            int closestCity = unvisitedCities.iterator().next();
            // loop through every unvisitedCity to find the closestCity
            for (Integer city : unvisitedCities) {
                // if the distance between the currentCity and the unvisitedCity is < the distance between
                // the currentCity and the closestCity
                if (distanceMatrix[currentCity][city] < distanceMatrix[currentCity][closestCity]) {
                    // set thet unvisitedCity as the closestCity
                    closestCity = city;
                }
            }
            // add the closestCity as the next city in the path
            nn.getPath().add(closestCity);
            // make the currentCity for the next iteration the current closestCity
            currentCity = closestCity;
            // remove the closestCity from the set of unvisitedCities
            unvisitedCities.remove(closestCity);
        }

        return calcPathDistance(nn.getPath());
    }

    private int calcPathDistance(ArrayList<Integer> path) {
        int pathDistance = 0;

        for (int i = 0; i < path.size() - 1; i++) {
            pathDistance += distanceMatrix[path.get(i)][path.get(i + 1)];
        }

        pathDistance += distanceMatrix[path.get(path.size() - 1)][path.get(0)];

        return pathDistance;
    }


    private void moveAnt(Ant ant, ArrayList<City> cities) {
        //Get the next city that ant will travel too
        int nextCityID = nextCity(cities, ant);

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

            pheromoneMatrix[cityA][cityB] *= 1.0 - delta;
            pheromoneMatrix[cityA][cityB] += ( delta * (1.0/bestAnt.getPathDistance()) );
//            pheromoneMatrix[cityA][cityB] += delta * (1 / bestAnt.getPathDistance());
//            pheromoneMatrix[cityA][cityB]
            pheromoneMatrix[cityA][cityB] = pheromoneMatrix[cityB][cityA];
        }

        int cityA = bestAnt.getPath().get(bestAnt.getPath().size() - 1);
        int cityB = bestAnt.getPath().get(0);

//        pheromoneMatrix[cityA][cityB] += delta * (1 / bestAnt.getPathDistance());
//            pheromoneMatrix[cityA][cityB]
        pheromoneMatrix[cityA][cityB] *= 1.0 - delta;
        pheromoneMatrix[cityA][cityB] += ( delta * (1.0/bestAnt.getPathDistance()) );
        pheromoneMatrix[cityA][cityB] = pheromoneMatrix[cityB][cityA];


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
        double pheromoneLevel = (1 - delta) * (pheromoneMatrix[currentCity][nextCity])
                + (delta / (citiesAmount * Lnn));

        pheromoneMatrix[currentCity][nextCity] = pheromoneLevel;
        pheromoneMatrix[nextCity][currentCity] = pheromoneLevel;

    }


    private int nextCity(ArrayList<City> cities, Ant ant) {

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

    private static int bestCity(double[] points) {

        // choose the city with the highest 'score' as the next city the ant will visit
        double maxScore = points[0];
        int bestCity = 0;

        for (int i = 1; i < points.length; i++) {
            if (points[i] > maxScore) {
                maxScore = points[i];
                bestCity = i;
            }
        }

        return bestCity;
    }

    private void distanceMatrix(ArrayList<City> cities) {
        distanceMatrix = new double[citiesAmount][citiesAmount];
        pheromoneMatrix = new double[citiesAmount][citiesAmount];


        for (int i = 0; i < citiesAmount; i++) {
            for (int j = 0; j < citiesAmount; j++) {
                distanceMatrix[i][j] = City.distanceTwoCities(cities.get(i).getLocation(), cities.get(j).getLocation());
                distanceMatrix[j][i] = distanceMatrix[i][j];
                pheromoneMatrix[i][j] = 0.00001;
                pheromoneMatrix[j][i] = 0.00001;
            }
        }
    }


}
