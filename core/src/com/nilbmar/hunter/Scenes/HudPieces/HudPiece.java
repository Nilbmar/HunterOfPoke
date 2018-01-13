package com.nilbmar.hunter.Scenes.HudPieces;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by sysgeek on 1/13/18.
 */

public interface HudPiece {

    void setPosition(float x, float y);
    Vector2 getPosition();
    void update();
}
