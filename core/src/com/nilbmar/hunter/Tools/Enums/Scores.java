package com.nilbmar.hunter.Tools.Enums;

/**
 * Created by sysgeek on 4/1/17.
 */

public enum Scores {
    BRICK(200), TREASURE(300),
    GOOMBA(400);

    private int points;

    Scores(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
