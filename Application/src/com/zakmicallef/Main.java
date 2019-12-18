package com.zakmicallef;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        FileInput file = new FileInput();
        ArrayList<City> cities = new ArrayList<City>();

        cities = file.readInput("test.tsp");

        for (City city : cities) {
            System.out.println(city.getCityId() + "\t" + city.getLocation().getX() + " " + city.getLocation().getY());
        }


//        GeneticAlgorithm.GeneticAlgo(cities)












    }
}
