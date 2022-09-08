package com.world;

import java.util.ArrayList;
import java.util.LinkedList;
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

    static int VISION_RANGE = 15;

    @JsonIgnore
    public ArrayList<Tile> vision;
    public double hunger = 100;
    public double fitness;
    public double prevFitness;
    public int e = 0;
    private Map map;
    @JsonIgnore
    public LinkedList<Memory> memory;
    public int busyDur;
    public boolean idle = busyDur == 0;
    private LifeManager lm;
    private int index;

    public Life(int x, int y, Map map, LifeManager lm, int index) {
        this.x = x;
        this.y = y;
        this.v = 0;
        this.r = 0;
        this.map = map;
        this.lm = lm;
        this.index = index;
        memory = new LinkedList<>();
    }

    public void onTick(double d) {
        this.hunger -= 0.2;
        if (Map.getDistance(getClosestFood(), this) == 0) {
            this.hunger = 100;
        }
        if (this.hunger < 0) {
            lm.reportDead(index);
        }

        this.fitness = hunger + foodMotivation();
        double b = this.busyDur - d;
        if (b < 0) {
            this.busyDur = 0;
        } else {
            this.busyDur -= d;
        }
        this.idle = busyDur == 0;
    }

    public Tile getClosestFood() {
        vision = map.getTiles(x, y, VISION_RANGE);
        Tile closestTile = null;
        int closestDistance = 5000;

        for (Tile t : vision) {
            if (t.getClass() == TileFood.class) {
                int dis = Map.getDistance(t, this);
                if (closestTile == null || closestDistance > dis) {
                    closestTile = t;
                    closestDistance = dis;
                }
            }
        }
        return closestTile;
    }

    // public void look() {
    // vision = map.getTiles(x, y, 6);
    // for (Tile t : vision) {
    // if (t.getClass() == TileFood.class) {
    // goTo(t.x, t.y);
    // }
    // }
    // }

    // public void goTo(int x, int y) {
    // this.x = x;
    // this.y = y;
    // }

    public int checkMove(int d) {
        if (d == 0 && this.y - 1 < 0) {
            return 2;
        }
        if (d == 1 && this.x + 1 > Map.MAP_TILES_X) {
            return 3;
        }
        if (d == 2 && this.y + 1 > Map.MAP_TILES_Y) {
            return 0;
        }
        if (d == 3 && this.x - 1 < 0) {
            return 2;
        }
        return d;
    }

    public void move(int d) {
        int dX = 0;
        int dY = 0;
        switch (d) {
        case 0:
            dY -= 1;
            break;
        case 1:
            dX += 1;
            break;
        case 2:
            dY += 1;
            break;
        case 3:
            dX -= 1;
            break;
        }

        this.y += dY;
        this.x += dX;
        this.busyDur += App.TICKRATE;
    }

    private int foodMotivation() {
        if (this.map == null) {
            return 0;
        }
        Tile cf = getClosestFood();
        if (cf == null) {
            return 0;
        }
        return (100 - (int) hunger) * (VISION_RANGE - Map.getDistance(cf, this));
    }

}
