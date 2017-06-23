package com.nilbmar.hunter.Components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.nilbmar.hunter.HunterOfPoke;
import com.nilbmar.hunter.Enums.Action;
import com.nilbmar.hunter.Enums.Direction;

/**
 * Created by sysgeek on 4/29/17.
 */

public class MoveComponent {
    private Direction currentDirection;
    private Direction previousDirection;
    private Action currentAction;
    private Action previousAction;

    private Body b2Body;

    private float acceleration;

    public MoveComponent(Body b2Body) {
        this.b2Body = b2Body;

        currentDirection = Direction.DOWN;
        previousDirection = Direction.DOWN;
        currentAction = Action.STILL;
        previousAction = Action.STILL;

        acceleration = 1;
    }

    public void move(Vector2 movement, int accl) {
        float maxVelocity = 32 / HunterOfPoke.PPM;
        float force = 0;
        float pixelsToMove = 8;
        previousDirection = currentDirection;
        movement.set(movement.x * maxVelocity, movement.y * maxVelocity);

        // Set Walking Animation and Direction
        if (!movement.isZero()) {
            setAction(Action.WALKING);
            setAcceleration(accl);

            // SET DIRECTION
            // If not moving up or down, set either left or right
            if (movement.y == 0) {
                if (movement.x > 0) {
                    setDirection(Direction.RIGHT);
                } else if (movement.x < 0) {
                    setDirection(Direction.LEFT);
                }
            } else if (movement.y > 0) {
                // If moving UP, check for side to side moveComponent as well, else set UP
                if (movement.x > 0) {
                    setDirection(Direction.UP_RIGHT);
                } else if (movement.x < 0) {
                    setDirection(Direction.UP_LEFT);
                } else {
                    setDirection(Direction.UP);
                }
            } else if (movement.y < 0) {
                // If moving DOWN, check for side to side moveComponent as well, else set DOWN
                if (movement.x > 0) {
                    setDirection(Direction.DOWN_RIGHT);
                } else if (movement.x < 0) {
                    setDirection(Direction.DOWN_LEFT);
                } else {
                    setDirection(Direction.DOWN);
                }
            }

            b2Body.setAwake(false);
            Vector2 desiredVelocity = new Vector2(movement.x * maxVelocity, movement.y * maxVelocity);
            force = acceleration * b2Body.getMass();
            desiredVelocity.x = (desiredVelocity.x - b2Body.getLinearVelocity().x) * pixelsToMove * force;
            desiredVelocity.y = (desiredVelocity.y - b2Body.getLinearVelocity().y) * pixelsToMove * force;

            b2Body.applyLinearImpulse(desiredVelocity, b2Body.getWorldCenter(), true);
        } else {
            // IF DIRECTION IS ZERO
            // Stop moveComponent
            b2Body.setAwake(false);
            setAction(Action.STILL);

            // TODO: CREATE/APPLY STATETIMER TO MEASURE
            // TIME PASSED SINCE KEYS RELEASED
            // IF NOT ENOUGH TIME PASSED, DON'T CHANGE DIRECTION
            //setDirection(previousDirection);

            // Face down when not moving - turned OFF
            //setDirection(Direction.DOWN);
        }
    }

    // Boosts from items or reset
    private void setAcceleration(int accl) {
        acceleration = accl;
    }

    public Direction getCurrentDirection() { return currentDirection; }
    private void setDirection(Direction dir) {
        previousDirection = currentDirection;
        currentDirection = dir;
    }

    public Action getCurrentAction() { return currentAction; }
    private void setAction(Action action) {
        previousAction = currentAction;
        currentAction = action;
    }

}
