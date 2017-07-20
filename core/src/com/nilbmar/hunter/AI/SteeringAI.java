package com.nilbmar.hunter.AI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nilbmar.hunter.AI.Utils.Behaviors;
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
    private Entity target;
    private Body body;

    private SteeringBehavior<Vector2> steerBehavior;
    private SteeringAcceleration<Vector2> steerOutput;

    private Vector2 position;

    private boolean tagged;

    private float boundingRadius;
    private float maxLinearSpeed;
    private float maxLinearAcceleration;
    private float maxAngularSpeed;
    private float maxAngularAcceleration;

    public SteeringAI(Entity entity, Entity target, float boundingRadius) {
        this.entity = entity;
        this.target = target;
        this.body = entity.getB2Body();

        // Was in the tutorial to create body
        // TODO: use instead for setting radius for Behaviors?
        this.boundingRadius = boundingRadius;

        tagged = false;

        maxLinearSpeed = 500;
        maxLinearAcceleration = 5000;
        maxAngularSpeed = 30;
        maxAngularAcceleration = 5;

        steerOutput = new SteeringAcceleration<Vector2>(new Vector2());

        position = body.getPosition();

        // Set initial steering behavior
        setSteerBehavior(Behaviors.Behavior.ARRIVE);
    }

    // Used to make the target (aka player) Steerable without adding it to the Player class
    public SteeringAI(Vector2 position) {
        this.position = position;
    }

    // TODO: SHOULD THIS EVEN BE?
    // PROBABLY MAKE IT ACCEPT ENUM STATES TO SWITCH BEHAVIORS
    public void arriveBehavior() {
        setSteeringBehavior(Behaviors.getBehavior(this, target, Behaviors.Behavior.ARRIVE));
        //setSteeringBehavior(Behaviors.arriveBehavior(this, target));
    }

    public SteeringBehavior<Vector2> getSteeringBehavior() { return steerBehavior; }
    public void setSteerBehavior(Behaviors.Behavior behavior) {
        steerBehavior = Behaviors.getBehavior(this, target, behavior);
    }
    public void setSteeringBehavior(SteeringBehavior<Vector2> steerBehavior) {
        this.steerBehavior = steerBehavior;
    }

    public Body getBody() { return body; }

    public void applySteering(float deltaTime) {
        boolean anyAccelerations = false;

        // Check for Linear Acceleration to apply
        if(!steerOutput.linear.isZero()) {
            // Apply scaled to delta
            Vector2 force = steerOutput.linear.scl(deltaTime);
            body.applyForceToCenter(force, true);
            anyAccelerations = true;
        }

        // TODO: YOUTUBE COMMENT SAYS THIS IS A BUG
        // Check for angle to turn body, so it will face the target
        if (steerOutput.angular != 0) {
            body.applyTorque(steerOutput.angular * deltaTime, true);
            anyAccelerations = true;
        }

        if (anyAccelerations) {
            Vector2 velocity = body.getLinearVelocity();
            float currentSpeedSquare = velocity.len2();
            // Cap linear speed at max
            if (currentSpeedSquare > (maxLinearSpeed * maxLinearSpeed)) {
                body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
                //body.setLinearVelocity(velocity.scl(maxLinearSpeed / (float) Math.sqrt(currentSpeedSquare)));
            }

            // Cap angular speed
            if (body.getAngularVelocity() > maxAngularSpeed) {
                body.setAngularVelocity(maxAngularSpeed);
            } else {
                Vector2 linearVel = getLinearVelocity();
                if (!linearVel.isZero()) {
                    float newOrientation = vectorToAngle(linearVel);
                    body.setAngularVelocity((newOrientation - getAngularVelocity()) * deltaTime);
                    body.setTransform(body.getPosition(), newOrientation);
                }
            }


        }
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
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
        return position;
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
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
        //return null;
        return (Location<Vector2>) new Vector2();
    }


    public void update(float deltaTime) {
        if (steerBehavior != null) {
            steerBehavior.calculateSteering(steerOutput);
            applySteering(deltaTime);

            // TODO: INSTEAD OF RESETING BEHAVIOR EACH TIME
            // USE ARRIVE.SETTARGET(TARGET)
            setSteerBehavior(Behaviors.Behavior.ARRIVE);
        }
    }
}
