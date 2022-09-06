package com.world;

import java.util.ArrayList;

import com.world.Tiles.Tile;
import com.world.Tiles.TileFood;
import com.world.Tiles.TileTree;
import com.world.Tiles.TileWater;
import com.world.Utils.OpenSimplexNoise;

public class Map {
    static public int MAP_TILES_X = 1000;
    static public int MAP_TILES_Y = 500;
    public int e = 0;

    public Tile[][] tiles;

    public Map() {
        resetMap();
        spawnTrees();
    }

    public void resetMap() {
        tiles = new Tile[MAP_TILES_Y][MAP_TILES_X];

        for (int y = 0; y < MAP_TILES_Y; y++) {
            for (int x = 0; x < MAP_TILES_X; x++) {
                tiles[y][x] = new Tile(x, y);
            }
        }
    }

    public ArrayList<Tile> getTiles(int x, int y, int r) {
        ArrayList<Tile> tTiles = new ArrayList<Tile>();
        for (int xi = x - r; xi < x + r; xi++) {
            for (int yi = y - r; yi < y + r; yi++) {
                if (xi >= 0 && yi >= 0 && xi < MAP_TILES_X && yi < MAP_TILES_Y) {
                    tTiles.add(tiles[yi][xi]);
                }
            }
        }
        return tTiles;
    }

    public void spawnTrees() {
        for (int y = 0; y < MAP_TILES_Y; y++) {
            for (int x = 0; x < MAP_TILES_X; x++) {
                OpenSimplexNoise noise = new OpenSimplexNoise(App.worldSeed);
                double simplex = noise.eval(x / App.treeFactor, y / App.treeFactor);

                double simNormal = (simplex + 1) / 2;
                // Gaussian gaus = new Gaussian(App.treeFactor);
                double treeRng = App.rng.nextDouble();
                // double value = gaus.Function1D(simNormal);
                float fSimNormal = (float) simNormal;
                double foodNoise = noise.eval(x / 2, y / 2);
                double foodNormal = (foodNoise - -1) / (1 - -1);

                if (foodNormal < 0.05) {
                    tiles[y][x] = new TileFood(x, y);
                    continue;
                }

                if ((treeRng / 0.02) * 0.01 > fSimNormal) {
                    tiles[y][x] = new TileTree(x, y);
                }

                if (fSimNormal < 0.1) {
                    tiles[y][x] = new TileWater(x, y);
                    // break;
                }
            }
        }
    }
}
