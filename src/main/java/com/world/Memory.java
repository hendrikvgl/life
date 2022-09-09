package com.world;

public class Memory {
    public int activity;
    public int param;
    public double fitness;

    public Memory(int a, int p) {
        this.activity = a;
        this.param = p;
    }

    public void setFitness(double f) {
        this.fitness = f;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt(activity + "" + param);
    }
}
