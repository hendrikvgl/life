package com.world;

import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class LifeManager {
    // @JsonSerialize(using = MapToArrayInit.class, as = String.class)
    public ConcurrentMap<Integer, Life> lifes;
    public int e = 1;
    private Map map;
    private LifeController lc;

    public LifeManager(Map map) {
        this.lifes = new ConcurrentHashMap<>();
        this.map = map;
        this.lc = new LifeController(this);
    }

    public void spawnLife() {
        int xrng = App.rng.nextInt(Map.MAP_TILES_X);
        int yrng = App.rng.nextInt(Map.MAP_TILES_Y);
        int s = lifes.size();
        Life life = new Life(xrng, yrng, map, this, s);
        lifes.put(s, life);
    }

    public void reportDead(int index) {
        lifes.remove(index);
    }

    public void spawnLife(int x) {
        for (int i = 0; i < x; i++) {
            spawnLife();
        }
    }

    public ArrayList<Life> getLifes() {
        ArrayList<Life> res = new ArrayList<>();
        for (Entry<Integer, Life> entry : this.lifes.entrySet()) {
            res.add(entry.getValue());
        }
        return res;
    }

}