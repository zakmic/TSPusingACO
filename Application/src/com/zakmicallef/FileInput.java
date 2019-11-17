package com.zakmicallef;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

class FileInput {

    ArrayList<City> readInput (String filePath) {
        try {
            return readCities(Files.readAllLines(Paths.get(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private ArrayList<City> readCities(List<String> readAllLines) {

    }

}
