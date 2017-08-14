package com.nilbmar.hunter.Components;

import com.badlogic.gdx.Gdx;
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
    private Array<DirectionComponent.Direction> walkIndex;
    private Array<DirectionComponent.Direction> stillIndex;
    private Array<DirectionComponent.Direction> useIndex;

    public FramesComponent(int walkSteps, int scaleSizeX, int scaleSizeY) {
        this.walkSteps = walkSteps;
        this.scaleSizeX = scaleSizeX;
        this.scaleSizeY = scaleSizeY;

        walkFrames = new Array<Vector2>();
        walkIndex = new Array<DirectionComponent.Direction>();

        stillFrames = new Array<Vector2>();
        stillIndex = new Array<DirectionComponent.Direction>();

        useFrames = new Array<Vector2>();
        useIndex = new Array<DirectionComponent.Direction>();


        setFrames();
    }

    private void setFrames() {

        // TODO: GET THE SCALE FOR X AND  Y FROM JSON

        // Multiple steps in walk cycles

        // Order that previously had to be set
        // UP, UP_LEFT, DOWN, DOWN_LEFT, LEFT
        DirectionComponent.Direction[] arrOfDirToSet
                = { DirectionComponent.Direction.UP, DirectionComponent.Direction. UP_LEFT,
                    DirectionComponent.Direction.DOWN, DirectionComponent.Direction.DOWN_LEFT,
                    DirectionComponent.Direction.LEFT };

        DirectionComponent.Direction dirToSet = null;

        for (int x = 0; x < arrOfDirToSet.length; x++) {
            dirToSet = arrOfDirToSet[x];

            for (int y = 0; y < walkSteps; y++) {
                setWalkFrames(dirToSet, y * scaleSizeX, 0);
            }

            switch (dirToSet) {
                case DOWN:
                    setStillFrames(dirToSet, 0, 0);
                    setUseFrames(dirToSet, 0, 0);
                    break;
                default:
                    setStillFrames(dirToSet, scaleSizeX, 0);
                    setUseFrames(dirToSet, scaleSizeX, 0);
                    break;
            }
        }

        /* TODO: AFTER USE FRAMES HAVE BEEN IMPLEMENTED, REMOVE THESE IF WORKING CORRECTLY
        this.setUseFrames(DirectionComponent.Direction.UP, scaleSizeX, 0);
        this.setUseFrames(DirectionComponent.Direction.UP_LEFT, scaleSizeX, 0); // TODO: THESE MAY HAVE CHANGED IT
        this.setUseFrames(DirectionComponent.Direction.DOWN, 0, 0);
        this.setUseFrames(DirectionComponent.Direction.DOWN_LEFT, 0, 0); // TODO: THESE MAY HAVE CHANGED IT
        this.setUseFrames(DirectionComponent.Direction.LEFT, scaleSizeX, 0);
        */
    }

    // Only need 3 because LEFT and RIGHT are the same, with LEFT flipped later
    private void setWalkFrames(DirectionComponent.Direction direction, float x, float y) {
        Vector2 position = new Vector2(x, y);
        walkFrames.add(position);
        walkIndex.add(direction);
    }

    public Vector2 getWalkFrames(DirectionComponent.Direction direction, int frame) {
        // Use LEFT facing frames when going right
        // which are then flipped
        switch (direction){
            case RIGHT:
                direction = DirectionComponent.Direction.LEFT;
                break;
            case UP_RIGHT:
                direction = DirectionComponent.Direction.UP_LEFT;
                break;
            case DOWN_RIGHT:
                direction = DirectionComponent.Direction.DOWN_LEFT;
                break;
        }

        // Get the index number for which walkFrame to use
        // based on the index of a direction in walkIndex
        // Adding frame adjusts the index for which frame
        // of a particular direction should be used
        // passed from the AnimationComponent
        int index = walkIndex.indexOf(direction, true) + frame;

        return walkFrames.get(index);
    }

    // Only need 3 because LEFT and RIGHT are the same, with LEFT flipped later
    private void setStillFrames(DirectionComponent.Direction direction, float x, float y) {
        Vector2 position = new Vector2(x, y);
        stillFrames.add(position);
        stillIndex.add(direction);
    }

    public Vector2 getStillFrames(DirectionComponent.Direction direction) {
        // Swap RIGHT variations for LEFT variations
        switch (direction){
            case RIGHT:
                direction = DirectionComponent.Direction.LEFT;
                break;
            case UP_RIGHT:
                direction = DirectionComponent.Direction.UP_LEFT;
                break;
            case DOWN_RIGHT:
                direction = DirectionComponent.Direction.DOWN_LEFT;
                break;
        }

        // Doesn't add frame because only one frame for STILL currently
        int index = stillIndex.indexOf(direction, true); // + frame;

        return stillFrames.get(index);
    }

    // Only need 3 because LEFT and RIGHT are the same, with LEFT flipped later
    private void setUseFrames(DirectionComponent.Direction direction, int x, int y) {
        Vector2 position = new Vector2(x, y);
        useFrames.add(position);
        useIndex.add(direction);
    }


    public Vector2 getUseFrames(DirectionComponent.Direction direction) {
        switch (direction){
            case RIGHT:
                direction = DirectionComponent.Direction.LEFT;
                break;
            case UP_RIGHT:
                direction = DirectionComponent.Direction.UP_LEFT;
                break;
            case DOWN_RIGHT:
                direction = DirectionComponent.Direction.DOWN_LEFT;
                break;
        }

        // Doesn't add frame because only one frame for USE currently
        int index = useIndex.indexOf(direction, true); // + frame;

        // TODO: ANIMATIONCOMPONENT DOESN'T CURRENTLY IMPLEMENT USEFRAMES AT ALL
        // TEST WHEN IMPLEMENTED AND REMOVE THIS LOG LINE
        Gdx.app.log("useFrames returned", direction + " " + useFrames.get(index).toString() + " TODO: REMOVE THIS LOG LINE AFTER TESTING"
            + " in the FramesComponent");
        return useFrames.get(index);
    }
}
