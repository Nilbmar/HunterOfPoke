package com.nilbmar.hunter.AI;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nilbmar.hunter.AI.Utils.SteeringUtil;
import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 7/1/17.
 *
 * AI: SteeringAI
 * Purpose: Have Entity move toward a point
 * with a possible "safe" distance
 */

public class SteeringAI implements Steerable<Vector2> {
    private Entity entity;
    private Body body;

    private SteeringBehavior<Vector2> steerBehavior;
    private SteeringAcceleration<Vector2> steerOutput;

    private boolean tagged;

    private float boundingRadius;
    private float maxLinearSpeed;
    private float maxLinearAcceleration;
    private float maxAngularSpeed;
    private float maxAngularAcceleration;

    public SteeringAI(Entity entity, float boundingRadius) {
        this.entity = entity;
        this.boundingRadius = boundingRadius;

        tagged = false;

        maxLinearSpeed = 500;
        maxLinearAcceleration = 5000;
        maxAngularSpeed = 30;
        maxAngularAcceleration = 5;

        steerOutput = new SteeringAcceleration<Vector2>(new Vector2());
        body = entity.getB2Body(); // TODO: STILL USING THIS?
    }

    public SteeringBehavior<Vector2> getSteeringBehavior() { return steerBehavior; }
    public void setSteeringBehavior(SteeringBehavior<Vector2> steerBehavior) {
        this.steerBehavior = steerBehavior;
    }

    public Body getBody() { return entity.getB2Body(); }

    public void applySteering(float deltaTime) {
        boolean anyAccelerations = false;

        // Check for Linear Acceleration to apply
        if(!steerOutput.linear.isZero()) {
            // Apply scaled to delta
            Vector2 force = steerOutput.linear.scl(deltaTime);
            entity.getB2Body().applyForceToCenter(force, true);
            anyAccelerations = true;
        }

        // TODO: YOUTUBE COMMENT SAYS THIS IS A BUG
        // Check for angle to turn body, so it will face the target
        if (steerOutput.angular != 0) {
            entity.getB2Body().applyTorque(steerOutput.angular * deltaTime, true);
            anyAccelerations = true;
        }

        if (anyAccelerations) {
            Vector2 velocity = entity.getB2Body().getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            // Cap linear speed at max
            if (currentSpeedSquare > (maxLinearSpeed * maxLinearSpeed)) {
                entity.getB2Body().setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
                //body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }

            // Cap angular speed
            if (entity.getB2Body().getAngularVelocity() > maxAngularSpeed) {
                entity.getB2Body().setAngularVelocity(maxAngularSpeed);
                //body.setAngularVelocity(maxAngularSpeed);
            } else {
                Vector2 linearVel = getLinearVelocity();
                if (!linearVel.isZero()) {
                    float newOrientation = vectorToAngle(linearVel);
                    entity.getB2Body().setAngularVelocity((newOrientation - getAngularVelocity()) * deltaTime);
                    entity.getB2Body().setTransform(entity.getB2Body().getPosition(), newOrientation);
                }
            }


        }
    }

    @Override
    public Vector2 getLinearVelocity() {
        return entity.getB2Body().getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return entity.getB2Body().getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return entity.getB2Body().getPosition();
    }

    @Override
    public float getOrientation() {
        return entity.getB2Body().getAngle();
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        // SteeringUtil comes from the video
        // not created yet
        return SteeringUtil.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        // SteeringUtil comes from the video
        // not created yet
        return SteeringUtil.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        // TODO:
        // video had a Vector2 newVector() { return new Vector2(); } function
        // that was implemented, but isn't in this version
        // could that be Location?

        // Location extends Vector<T>
        // https://libgdx.badlogicgames.com/gdx-ai/docs/com/badlogic/gdx/ai/utils/Location.html
        // TODO: NO CLUE IF THIS WORKS AT ALL, HOBBLED TOGETHER
        // WAS NULL
        return (Location<Vector2>) new Vector2();
    }


    public void update(float deltaTime) {
        if (steerBehavior != null) {
            steerBehavior.calculateSteering(steerOutput);
            applySteering(deltaTime);
        }
    }
}
