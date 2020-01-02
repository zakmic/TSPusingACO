package com.zakmicallef;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        FileInput file = new FileInput();
        ACO aco = new ACO();


        ArrayList<City> cities = file.readInput("test.tsp");

//        for (City city : cities) {
//            System.out.println(city.getCityId() + "\t" + city.getLocation().getX() + " " + city.getLocation().getY());
//        }

        ArrayList<Double> distances = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            distances.add(aco.AntColonyOptimization(cities));
        }

        double total = 0;

        for (double pathDistance : distances) {
            total += pathDistance;
        }

        double avg = total / distances.size();

        System.out.println("\nAverage Path Distance:\t" + avg);


    }
}
