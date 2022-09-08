package com.world.Tiles;

import java.awt.Color;

import com.fasterxml.jackson.annotation.JsonValue;

public class Tile {
    public int x;
    public int y;
    public boolean solid = false;
    public String texture = "#ffffff";
    public int tile = 0;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Tile(int x, int y, String texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    @JsonValue
    public int getTile() {
        return this.tile;
    }

    static String getColorFromRange(float range) {
        Color color = new Color(range, range, range);
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
