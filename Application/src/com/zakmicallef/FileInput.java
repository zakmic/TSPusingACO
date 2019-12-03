package com.zakmicallef;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class FileInput {

    ArrayList<City> readInput(String filePath) {
        try {
            return readCities(Files.readAllLines(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private ArrayList<City> readCities(List<String> readAllLines) {

        ArrayList<City> cities = new ArrayList<City>();

        for (String cityDetails : readAllLines) {
            if (!cityDetails.isEmpty() && cityDetails.substring(0, 1).matches("[0-9]")) {
                City city = new City();
                Point location = new Point();
                String[] cityFeilds = cityDetails.split("\\s+");
                city.setCityId(Integer.parseInt(cityFeilds[0]));
                location.setX(Double.parseDouble(cityFeilds[1]));
                location.setY(Double.parseDouble(cityFeilds[2]));
                city.setLocation(location);
                System.out.println(city.getCityId() + "\t" + location.getX() + " " + location.getY());
                cities.add(city);
            }
        }
        return cities;
    }


}
