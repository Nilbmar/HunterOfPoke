package com.nilbmar.hunter.Components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Tools.Loaders.AnimationData;
import com.nilbmar.hunter.Tools.Loaders.AnimationLoader;

/**
 * Created by sysgeek on 5/4/17.
 *
 * Purpose: Grab the correct frame from an AnimationComp
 */

public class FramesComponent {
    private AnimationLoader animLoader;
    private AnimationData animData;
    private String animFile;
    private int walkFramesCount;
    private int useFramesCount;
    private int stillFramesCount;
    private int scaleSizeX;
    private int scaleSizeY;

    private Array<Vector2> walkFrames;
    private Array<Vector2> useFrames;
    private Array<Vector2> stillFrames;
    private Array<DirectionComponent.Direction> walkIndex;
    private Array<DirectionComponent.Direction> stillIndex;
    private Array<DirectionComponent.Direction> useIndex;

    public FramesComponent(int scaleSizeX, int scaleSizeY) {
        this.scaleSizeX = scaleSizeX;
        this.scaleSizeY = scaleSizeY;

        walkFrames = new Array<Vector2>();
        walkIndex = new Array<DirectionComponent.Direction>();

        stillFrames = new Array<Vector2>();
        stillIndex = new Array<DirectionComponent.Direction>();

        useFrames = new Array<Vector2>();
        useIndex = new Array<DirectionComponent.Direction>();

        animLoader = new AnimationLoader();

        // TODO: REMOVE setFrames() CALL
        //setFrames();
    }

    //public void setAnimFile(String animFile) { this.animFile = animFile; }

    public int getWalkFramesCount() { return walkFramesCount; }
    public int getUseFramesCount() { return useFramesCount; }
    public int getStillFramesCount() { return stillFramesCount; }
    public void setFramesCounts() {
        if (animFile != null) {
            animLoader.setFile(animFile);
            animLoader.load();
            animData = animLoader.getData();
            walkFramesCount = animData.getWalkFramesCount();
            useFramesCount = animData.getUseFramesCount();
            stillFramesCount = animData.getStillFramesCount();
            if (animData.getWalkFramesArr() != null) {
                Gdx.app.log("AnimationData in FramesComponent", animData.getWalkFramesArr().toString());
            } else {
                Gdx.app.log("AnimationData in FramesComponenet", "getWalkFramesArr is null");
            }
        } else {
            Gdx.app.log("animFile", " is null");
        }
    }

    public void setFrames(String animFile) {
        this.animFile = animFile;
        setFramesCounts();
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

            for (int y = 0; y < walkFramesCount; y++) {
                setWalkFrames(dirToSet, y * scaleSizeX, 0);

                // TODO: REMOVE THIS - TOOK OUT USEFRAMES SWITCH FROM HERE
            }

            for (int y = 0; y < useFramesCount; y++) {
                switch (dirToSet) {
                    case DOWN:
                        setUseFrames(dirToSet, 0, 0);
                        break;
                    default:
                        setUseFrames(dirToSet, scaleSizeX, 0);
                        break;
                }
            }

            for (int y = 0; y < stillFramesCount; y++) {
                switch (dirToSet) {
                    case DOWN:
                        setStillFrames(dirToSet, 0, 0);
                        break;
                    default:
                        setStillFrames(dirToSet, scaleSizeX, 0);
                        break;
                }
            }
        }
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


    public Vector2 getUseFrames(DirectionComponent.Direction direction, int frame) {
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

        int index = useIndex.indexOf(direction, true) + frame;

        return useFrames.get(index);
    }
}
