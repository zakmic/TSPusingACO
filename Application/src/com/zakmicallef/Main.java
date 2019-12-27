package com.zakmicallef;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        FileInput file = new FileInput();
        ACO aco = new ACO();


        ArrayList<City> cities = file.readInput("test.tsp");

//        for (City city : cities) {
//            System.out.println(city.getCityId() + "\t" + city.getLocation().getX() + " " + city.getLocation().getY());
//        }

        aco.AntColonyOptimization(cities);


    }
}
