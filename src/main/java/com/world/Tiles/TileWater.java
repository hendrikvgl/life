package com.world.Tiles;

import com.fasterxml.jackson.annotation.JsonValue;

public class TileWater extends Tile {
    public TileWater(int x, int y) {
        super(x, y);
    }

    public boolean solid = true;
    public String texture = "#1573ed";
    public int tile = 3;

    @JsonValue
    public int getTile() {
        return this.tile;
    }
}
