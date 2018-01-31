package com.nilbmar.hunter.AI.Utils;

import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.Flee;
import com.badlogic.gdx.ai.steer.behaviors.Seek;
import com.badlogic.gdx.ai.steer.behaviors.Wander;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.MathUtils;
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
    public enum Behavior { ARRIVE, SEEK, WANDER, FLEE }

    public static SteeringBehavior<Vector2> getBehavior(SteeringAI steerable, AITarget target, Behavior behavior) {
        SteeringBehavior<Vector2> steeringBehavior = null;

        switch(behavior) {
            case SEEK:
                steeringBehavior = seekBehavior(steerable, target);
                break;
            case ARRIVE:
                steeringBehavior = arriveBehavior(steerable, target);
                break;
            case WANDER:
                steeringBehavior = wanderBehavior(steerable, target);
                break;
            case FLEE:
                steeringBehavior = fleeBehavior(steerable, target);
                break;
        }

        return steeringBehavior;
    }

    private static SteeringBehavior<Vector2> fleeBehavior(SteeringAI steerable, AITarget target) {
        return new Flee<Vector2>(steerable, new SteeringAI(target.getPosition()));
    }

    private static SteeringBehavior<Vector2> seekBehavior(SteeringAI steerable, AITarget target) {
        return new Seek<Vector2>(steerable, new SteeringAI(target.getPosition()));
    }

    private static SteeringBehavior<Vector2> wanderBehavior(SteeringAI steerable, AITarget target) {
        Wander<Vector2> wander =  new Wander<Vector2>(steerable)
                .setFaceEnabled(false) // let wander behaviour manage facing
                .setWanderOffset(2 / HunterOfPoke.PPM) // distance away from entity to set target
                .setWanderOrientation(0f) // the initial orientation
                .setWanderRadius(0.05f / HunterOfPoke.PPM) // size of target
                .setWanderRate(0.005f / HunterOfPoke.PPM); // higher values = more spinning

        return wander;
    }

    private static SteeringBehavior<Vector2> arriveBehavior(SteeringAI steerable, AITarget target) {
        Arrive<Vector2> arrive = new Arrive<Vector2>(steerable,
                new SteeringAI(target.getPosition()));

        arrive.setTimeToTarget(2 / HunterOfPoke.PPM)
                .setArrivalTolerance(7 / HunterOfPoke.PPM)
                .setDecelerationRadius(10f);

        return arrive;
    }
}
