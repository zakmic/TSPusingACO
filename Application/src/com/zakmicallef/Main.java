package com.zakmicallef;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        File dir = new File("dataset");
        FileFilter filter = new TspFileFilter();
        File[] tspFiles = dir.listFiles(filter);

        for (File file : tspFiles) {
            FileInput fileObject = new FileInput();
            ACO aco = new ACO();
            System.out.println("File:\t" + file);
            ArrayList<City> cities = fileObject.readFile(file.getPath());
            aco.AntColonyOptimization(cities);
            System.out.println();
        }

    }

}
