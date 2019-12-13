package com.zakmicallef;

public class Chromosome {

    int[] path;
    double fitness;

    Chromosome(){
    }

    public Chromosome(int[] path, double fitness) {
        this.path = path;
        this.fitness = fitness;
    }

    public int[] getPath() {
        return path;
    }

    public void setPath(int[] path) {
        this.path = path;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
