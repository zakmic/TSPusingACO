package com.zakmicallef;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class GeneticAlgorithm {

    // 2D-Array of distances between every pair of cities
    private double[][] distanceMatrix;

    Chromosome GeneticAlgo(ArrayList<City> cities) {

        ArrayList<Chromosome> chromosomes = new ArrayList<>();
        double crossoverRate = 0.8;

        //Initialize Distance Matrix
        distanceMatrix(cities);

        //Generate Initial Population of chromosomes
        ArrayList<Chromosome> population = generatePopulation(cities, 100);

        //Calculate and Sort chromosomes by fitness
        populationFitness(population);

        //Sorting List according to chromosome fitness
//        population.sort((a, b) -> Double.compare(a.getFitness(), b.getFitness()));
        population.sort(Comparator.comparingDouble(Chromosome::getFitness));

        //Crossover Chromosomes
        ArrayList<Chromosome> children = crossover(population, crossoverRate);


        //Mutate Children

        //Update Population

        return chromosomes.get(0);
    }

    private ArrayList<Chromosome> crossover(ArrayList<Chromosome> population, double crossoverRate) {

    }

    private void populationFitness(ArrayList<Chromosome> population) {

        for (Chromosome chromosome : population) {
            double pathDistance = pathDistance(chromosome);
            chromosome.fitness = (1 / (pathDistance)) * 1000;
        }

    }

    private double pathDistance(Chromosome chromosome) {
        double totalDistance = 0;

        //Distance between every city till the final city
        for (int i = 0; i < chromosome.path.length - 1; i++) {
            totalDistance += distanceMatrix[chromosome.path[i]][chromosome.path[i + 1]];
        }

        //Distance betwwen the final city
        totalDistance += distanceMatrix[chromosome.path[chromosome.path.length - 1]][chromosome.path[0]];

        return totalDistance;
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
            Chromosome chromosome = new Chromosome();
            Collections.shuffle(initPath);
            chromosome.path = initPath.stream().mapToInt(Integer::intValue).toArray();
            population.add(chromosome);
        }
        return population;
    }

    private void distanceMatrix(ArrayList<City> cities) {
        distanceMatrix = new double[cities.size()][cities.size()];

        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                distanceMatrix[i][j] = City.distanceTwoCities(cities.get(i).getLocation(), cities.get(j).getLocation());
                distanceMatrix[j][i] = distanceMatrix[i][j];
            }
        }
    }


}
