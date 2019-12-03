package com.zakmicallef;

import java.util.ArrayList;

public class ACO {

    void newPath(ArrayList<City> cities) {

        Ant ant = new Ant(cities.get(0).getCityId());
        ArrayList<Integer> cityIDs = new ArrayList<>();

        for (City city : cities) {
            cityIDs.add(city.getCityId());
        }

//        ant.setPath();
    }
}
