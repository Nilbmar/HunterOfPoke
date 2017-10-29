package com.nilbmar.hunter.AI;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by sysgeek on 10/29/17.
 */

public class AITarget {
    private Vector2 position;

    public AITarget(Vector2 position) {
        this.position = position;
    }

    public Vector2 getPosition() { return position; }
    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
