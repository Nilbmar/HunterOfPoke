package com.nilbmar.hunter.Tools.Enums;

/**
 * Created by sysgeek on 4/1/17.
 */

public enum Layers {
    BACKGROUND(0), SCENERY_1(1), GRAPHICS(2), SCENERY_2(3),
    GROUND(4), PICKUPS(5), SPAWN(6), PLAYER(7);

    private int index;

    Layers(int index) { this.index = index; }

    public int getIndex() {
        return index;
    }
}
