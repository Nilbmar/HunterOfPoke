package com.nilbmar.hunter.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Enums.Action;

/**
 * Created by sysgeek on 4/29/17.
 *
 * Purpose: Allow movement of separate entities (Player and Bullets)
 * Enemies use SteeringAI instead
 */

public class MoveComponent {
    private DirectionComponent.Direction currentDirection;
    private DirectionComponent.Direction previousDirection;
    private Action currentAction;
    private Action previousAction;

    private Body b2Body;

    private Vector2 movement;
    private float acceleration;
    private float maxVelocity = 32 / HunterOfPoke.PPM;

    public MoveComponent(Body b2Body) {
        this.b2Body = b2Body;

        currentDirection = DirectionComponent.Direction.DOWN;
        previousDirection = DirectionComponent.Direction.DOWN;
        currentAction = Action.STILL;
        previousAction = Action.STILL;

        movement = new Vector2(0, 0);
        acceleration = 1;
    }

    // Used by action function
    // and by entities that don't need extra actions (USE/STILL)
    // ie. Bullets only need to move until destroyed
    public void move(Vector2 movement, int accl) {
        float force = 0;
        float pixelsToMove = 8;
        movement.set(movement.x * maxVelocity, movement.y * maxVelocity);

        //setAction(Action.WALKING);
        setAcceleration(accl);

        // SET DIRECTION
        // If not moving up or down, set either left or right
        if (movement.y == 0) {
            if (movement.x > 0) {
                setDirection(DirectionComponent.Direction.RIGHT);
            } else if (movement.x < 0) {
                setDirection(DirectionComponent.Direction.LEFT);
            }
        } else if (movement.y > 0) {
            // If moving UP, check for side to side moveComponent as well, else set UP
            if (movement.x > 0) {
                setDirection(DirectionComponent.Direction.UP_RIGHT);
            } else if (movement.x < 0) {
                setDirection(DirectionComponent.Direction.UP_LEFT);
            } else {
                setDirection(DirectionComponent.Direction.UP);
            }
        } else if (movement.y < 0) {
            // If moving DOWN, check for side to side moveComponent as well, else set DOWN
            if (movement.x > 0) {
                setDirection(DirectionComponent.Direction.DOWN_RIGHT);
            } else if (movement.x < 0) {
                setDirection(DirectionComponent.Direction.DOWN_LEFT);
            } else {
                setDirection(DirectionComponent.Direction.DOWN);
            }
        }

        b2Body.setAwake(false);
        Vector2 desiredVelocity = new Vector2(movement.x * maxVelocity, movement.y * maxVelocity);
        force = acceleration * b2Body.getMass();
        desiredVelocity.x = (desiredVelocity.x - b2Body.getLinearVelocity().x) * pixelsToMove * force;
        desiredVelocity.y = (desiredVelocity.y - b2Body.getLinearVelocity().y) * pixelsToMove * force;

        b2Body.applyLinearImpulse(desiredVelocity, b2Body.getWorldCenter(), true);
    }

    public void action(Vector2 movement, int accl) {
        this.movement = movement;
        previousDirection = currentDirection;


        // Set Walking Animation and DirectionComponent.Direction
        switch (currentAction) {
            case WALKING:
                move(movement, accl);
                break;
            case USE:
                // IF DIRECTION IS ZERO
                // Stop moveComponent
                b2Body.setAwake(false);
                // TODO: POSSIBLY ADD BACK IN setAction(Action.STILL);
                //setAction(Action.STILL);
                break;
            case STILL:
                // IF DIRECTION IS ZERO
                // Stop moveComponent
                b2Body.setAwake(false);
                // TODO: POSSIBLY ADD BACK IN setAction(Action.STILL);
                //setAction(Action.STILL);
                break;
        }
    }

    public boolean isMoving() { return !movement.isZero(); }

    // Boosts from items or reset
    private void setAcceleration(int accl) {
        acceleration = accl;
    }

    public DirectionComponent.Direction getCurrentDirection() { return currentDirection; }
    private void setDirection(DirectionComponent.Direction dir) {
        previousDirection = currentDirection;
        currentDirection = dir;
    }

    public Action getCurrentAction() { return currentAction; }
    public void setAction(Action action) {
        previousAction = currentAction;
        currentAction = action;
    }

}
