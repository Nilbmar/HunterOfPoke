package com.nilbmar.hunter.Components;


/**
 * Created by sysgeek on 7/28/17.
 */

public class DirectionComponent implements Component {


    public enum Direction { UP, DOWN, LEFT, RIGHT,
        DOWN_LEFT, DOWN_RIGHT, UP_LEFT, UP_RIGHT }

    private Direction currentDirection;
    private Direction previousDirection;

    public DirectionComponent() {
        currentDirection = Direction.DOWN;
        previousDirection = Direction.DOWN;
    }

    public Direction getPreviouDirection() { return previousDirection; } // Set in action()
    public Direction getDirection() { return currentDirection; } // Set in action()
    public void setDirection(Direction dir) {
        previousDirection = currentDirection;
        currentDirection = dir;
    }
}
