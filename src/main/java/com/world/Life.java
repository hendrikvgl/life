package com.world;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.world.Tiles.Tile;
import com.world.Tiles.TileFood;

public class Life {
    public int x;
    public int y;
    public double r;
    public double v;

    @JsonIgnore
    public double hunger;
    public int e = 0;
    private Map map;

    public Life(int x, int y, Map map) {
        this.x = x;
        this.y = y;
        this.v = 0;
        this.r = 0;
        this.map = map;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                wander();
                look();
            }
        }, 0, 200);
    }

    public void look() {
        ArrayList<Tile> vision = map.getTiles(x, y, 6);
        for (Tile t : vision) {
            if (t.getClass() == TileFood.class) {
                goTo(t.x, t.y);
            }
        }
    }

    public void goTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void wander() {
        this.x = this.x + App.rng.nextInt(-1, 2);
        this.y = this.y + App.rng.nextInt(-1, 2);
    }

}
