package com.nilbmar.hunter.AI.Utils;

import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.math.Vector2;
import com.nilbmar.hunter.AI.AITarget;
import com.nilbmar.hunter.AI.SteeringAI;
import com.nilbmar.hunter.HunterOfPoke;

/**
 * Created by sysgeek on 7/20/17.
 * AI.Utils: Behaviors
 * Purpose: Separate implementation of Steering Behaviors
 * and use in a static context
 */

public abstract class Behaviors {
    public enum Behavior { ARRIVE, SEEK }

    public static SteeringBehavior<Vector2> getBehavior(SteeringAI steerable, AITarget target, Behavior behavior) {
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

    public static SteeringBehavior<Vector2> seekBehavior(SteeringAI steerable, AITarget target) {
        return new Seek<Vector2>(steerable, new SteeringAI(target.getPosition()));

    }

    public static SteeringBehavior<Vector2> arriveBehavior(SteeringAI steerable, AITarget target) {
        Arrive<Vector2> arrive = new Arrive<Vector2>(steerable,
                new SteeringAI(target.getPosition()));

        arrive.setTimeToTarget(2 / HunterOfPoke.PPM)
                .setArrivalTolerance(5 / HunterOfPoke.PPM)
                .setDecelerationRadius(10);

        return arrive;
    }
}
