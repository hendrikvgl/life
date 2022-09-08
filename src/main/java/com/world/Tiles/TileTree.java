package com.world.Tiles;

import com.fasterxml.jackson.annotation.JsonValue;

public class TileTree extends Tile {

    public TileTree(int x, int y) {
        super(x, y);
    }

    public boolean solid = true;
    public String texture = "#237028";
    public int tile = 1;

    @JsonValue
    public int getTile() {
        return this.tile;
    }
}
