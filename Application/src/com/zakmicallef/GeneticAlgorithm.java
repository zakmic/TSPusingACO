package com.zakmicallef;

import java.util.ArrayList;

public class GeneticAlgorithm {


    Chromosome GeneticAlgo(ArrayList<City> cities) {

        ArrayList<Chromosome> chromosomes = new ArrayList<>();

        //Initialize Distance Matrix
        distanceMatrix(cities);

        //Generate Initial Population of chromosomes
        ArrayList<Chromosome> =generatePopulation(cities, 100);


        //Calc and Sort chromosomes by fitness

        //Crossover Chromosomes

        //Mutate Children

        //Update Population

        return chromosomes.get(0);
    }

    private ArrayList<Chromosome> generatePopulation(ArrayList<City> cities, int populationSize) {
        ArrayList<Chromosome> population = new ArrayList<Chromosome>();


        int pathLength = cities.size();

        //initPath is a list of CityID's
        ArrayList<Integer> initPath = new ArrayList<>();

        for (City city : cities) {
            initPath.add(city.getCityId());
        }

        for (int i = 0; i < populationSize; i++) {
            //Shuffle Arraylist
        }

    }

    private void distanceMatrix(ArrayList<City> cities) {
        double[][] distanceMatrix = new double[cities.size()][cities.size()];

        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                distanceMatrix[i][j] = City.distanceTwoCities(cities.get(i).getLocation(), cities.get(j).getLocation());
                distanceMatrix[j][i] = distanceMatrix[i][j];
            }
        }
    }




}
