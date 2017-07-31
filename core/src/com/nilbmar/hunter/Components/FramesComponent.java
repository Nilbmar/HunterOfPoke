package com.nilbmar.hunter.Components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by sysgeek on 5/4/17.
 *
 * Purpose: Grab the correct frame from an AnimationComp
 */

public class FramesComponent {
    private int walkSteps;
    private int scaleSizeX;
    private int scaleSizeY;

    private Array<Vector2> walkFrames;
    private Array<Vector2> useFrames;
    private Array<Vector2> stillFrames;

    public FramesComponent(int walkSteps, int scaleSizeX, int scaleSizeY) {
        this.walkSteps = walkSteps;
        this.scaleSizeX = scaleSizeX;
        this.scaleSizeY = scaleSizeY;
        walkFrames = new Array<Vector2>();
        useFrames = new Array<Vector2>(3);
        stillFrames = new Array<Vector2>(3);

        setFrames();
    }

    private void setFrames() {

        /* YOU MUST GO IN ORDER OF UP, UP_LEFT, DOWN, DOWN_LEFT, LEFT*/
        // Multiple steps in walk cycles
        for (int x = 0; x < walkSteps; x++) {
            this.setWalkFrames(DirectionComponent.Direction.UP, x * scaleSizeX, 0);
        }
        for (int x = 0; x < walkSteps; x++) {
            this.setWalkFrames(DirectionComponent.Direction.UP_LEFT, x * scaleSizeX, 0);
        }

        for (int x = 0; x < walkSteps; x++) {
            this.setWalkFrames(DirectionComponent.Direction.DOWN, x * scaleSizeX, 0);
        }

        for (int x = 0; x < walkSteps; x++) {
            this.setWalkFrames(DirectionComponent.Direction.DOWN_LEFT, x * scaleSizeX, 0);
        }

        for (int x = 0; x < walkSteps; x++) {
            this.setWalkFrames(DirectionComponent.Direction.LEFT, x * scaleSizeX, 0);
        }

        this.setUseFrames(DirectionComponent.Direction.UP, scaleSizeX, 0);
        this.setUseFrames(DirectionComponent.Direction.UP_LEFT, scaleSizeX, 0); // TODO: THESE MAY HAVE CHANGED IT
        this.setUseFrames(DirectionComponent.Direction.DOWN, 0, 0);
        this.setUseFrames(DirectionComponent.Direction.DOWN_LEFT, 0, 0); // TODO: THESE MAY HAVE CHANGED IT
        this.setUseFrames(DirectionComponent.Direction.LEFT, scaleSizeX, 0);

        this.setStillFrames(DirectionComponent.Direction.UP, scaleSizeX, 0);
        this.setStillFrames(DirectionComponent.Direction.UP_LEFT, scaleSizeX, 0);
        this.setStillFrames(DirectionComponent.Direction.DOWN, 0, 0);
        this.setStillFrames(DirectionComponent.Direction.DOWN_LEFT, scaleSizeX, 0);
        this.setStillFrames(DirectionComponent.Direction.LEFT, scaleSizeX, 0);
    }

    public Vector2 getWalkFrames(DirectionComponent.Direction direction, int x) {
        Vector2 position = new Vector2();
        int index = x;

        switch (direction) {
            case UP:
                index = x;
                break;
            case UP_LEFT:
                index = x + 4;
            case UP_RIGHT:
                break;
            case DOWN:
                index = x + 8;
                break;
            case DOWN_LEFT:
            case DOWN_RIGHT:
                index = x + 12;
                break;
            case LEFT:
            case RIGHT:
                index = x + 16;
                break;
        }
        position.x = walkFrames.get(index).x;
        position.y = walkFrames.get(index).y;

        return position;
    }

    public Vector2 getUseFrames(DirectionComponent.Direction direction) {
        Vector2 position = new Vector2();
        switch(direction) {
            case UP:
                position.x = useFrames.get(0).x;
                position.y = useFrames.get(0).y;
                break;
            case DOWN:
                position.x = useFrames.get(1).x;
                position.y = useFrames.get(1).y;
                break;
            case LEFT:
            case RIGHT:
                position.x = useFrames.get(2).x;
                position.y = useFrames.get(2).y;
                break;
        }

        return position;
    }

    public Vector2 getStillFrames(DirectionComponent.Direction direction) {
        int index = 0;
        Vector2 position = new Vector2();
        switch(direction) {
            case UP:
                index = 0;
                break;
            case UP_LEFT:
            case UP_RIGHT:
                index = 1;
                break;
            case DOWN:
                index = 2;
                break;
            case DOWN_LEFT:
            case DOWN_RIGHT:
                index = 3;
                break;
            case LEFT:
            case RIGHT:
                index = 4;
                break;
        }

        position.x = stillFrames.get(index).x;
        position.y = stillFrames.get(index).y;

        return position;
    }

    // Only need 3 because LEFT and RIGHT are the same, with LEFT flipped later
    public void setWalkFrames(DirectionComponent.Direction direction, float x, float y) {
        Vector2 position = new Vector2(x, y);
        walkFrames.add(position);
    }

    // Only need 3 because LEFT and RIGHT are the same, with LEFT flipped later
    public void setUseFrames(DirectionComponent.Direction direction, int x, int y) {
        Vector2 position = new Vector2(x, y);
        useFrames.add(position);
    }

    // Only need 3 because LEFT and RIGHT are the same, with LEFT flipped later
    public void setStillFrames(DirectionComponent.Direction direction, int x, int y) {
        Vector2 position = new Vector2(x, y);
        stillFrames.add(position);
    }

}
