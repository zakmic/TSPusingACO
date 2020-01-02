package com.zakmicallef;

import java.io.File;
import java.io.FileFilter;

public class TspFileFilter implements FileFilter {
    public boolean accept(File file) {
        return file.getName().toLowerCase().endsWith("tsp");
    }
}
