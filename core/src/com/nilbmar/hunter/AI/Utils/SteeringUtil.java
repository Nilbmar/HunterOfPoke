package com.nilbmar.hunter.AI.Utils;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by sysgeek on 7/15/17.
 *
 * AI.Utils: SteeringUtil
 * Purpose: Calculate orientation of a Body
 * based on where its going and speed
 *
 * Taken from tutorial video on gdx-ai steering behavior
 * https://youtu.be/pnKcuJQT31A?t=985
 */

public final class SteeringUtil {
    public static float vectorToAngle(Vector2 vector) {
        return (float) Math.atan2(-vector.x, vector.y);
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -(float) Math.sin(angle);
        outVector.y = (float) Math.cos(angle);

        return outVector;
    }
}
