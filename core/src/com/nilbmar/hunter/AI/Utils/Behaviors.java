package com.nilbmar.hunter.AI.Utils;

import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.nilbmar.hunter.AI.SteeringAI;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.HunterOfPoke;

/**
 * Created by sysgeek on 7/20/17.
 */

public abstract class Behaviors {
    public static enum Behavior { ARRIVE, SEEK }

    public static SteeringBehavior<Vector2> getBehavior(SteeringAI steerable, Entity target, Behavior behavior) {
        SteeringBehavior<Vector2> steeringBehavior = null;

        switch(behavior) {
            case SEEK:
                steeringBehavior = arriveBehavior(steerable, target);
                break;
            case ARRIVE:
                steeringBehavior = arriveBehavior(steerable, target);
                break;
        }

        return steeringBehavior;
    }

    public static SteeringBehavior<Vector2> arriveBehavior(SteeringAI steerable, Entity target) {
        Arrive<Vector2> arrive = new Arrive<Vector2>(steerable,
                new SteeringAI(new Vector2(target.getB2Body().getPosition())));

        arrive.setTimeToTarget(10 / HunterOfPoke.PPM)
                .setArrivalTolerance(5 / HunterOfPoke.PPM)
                .setDecelerationRadius(50);

        return arrive;
    }
}
