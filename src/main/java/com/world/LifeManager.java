package com.world;

import java.util.ArrayList;

public class LifeManager {
    public ArrayList<Life> lifes;
    public int e = 1;
    private Map map;

    public LifeManager(Map map) {
        this.lifes = new ArrayList<>();
        this.map = map;
    }

    public void spawnLife() {
        int xrng = App.rng.nextInt(Map.MAP_TILES_X);
        int yrng = App.rng.nextInt(Map.MAP_TILES_Y);
        Life life = new Life(xrng, yrng, map);
        lifes.add(life);
    }

    public void spawnLife(int x) {
        for (int i = 0; i < x; i++) {
            spawnLife();
        }
    }
}
