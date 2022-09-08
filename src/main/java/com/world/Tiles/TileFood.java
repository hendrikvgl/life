package com.world.Tiles;

import com.fasterxml.jackson.annotation.JsonValue;

public class TileFood extends Tile {
    public TileFood(int x, int y) {
        super(x, y);
    }

    public boolean solid = true;
    public String texture = "#cf2317";
    public int tile = 2;

    @JsonValue
    public int getTile() {
        return this.tile;
    }
}
