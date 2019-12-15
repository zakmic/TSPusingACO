package com.zakmicallef;

public class Chromosome {

    int[] path;
    double fitness;

    Chromosome(){
    }

    public Chromosome(double fitness, int[] path) {
        this.fitness = fitness;
        this.path = path;
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
