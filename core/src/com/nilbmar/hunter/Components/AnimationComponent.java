package com.nilbmar.hunter.Components;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Enums.Action;

/*
 * Created by sysgeek on 5/4/17.
 *
 * Component: AnimationComponent
 * Purpose: Get the correct frames of animation from a texture atlas
 * based on current direction and action
 *
 * /* TODO:
 * THIS FUNCTION CURRENTLY GETS ANIMATION ON THE FLY
 * THAT MIGHT BE VERY SLOW COMPARED TO CREATING IN CONSTRUCTOR
 * POSSIBLY CHANGE SO THAT IT CAN BE USED IN THE CONSTRUCTOR
 * FOR EACH TYPE OF ANIMATION, PASSING PARAMETERS

 * Animation charWalkRight = new Animation(0.1f,
 * getAnimation(screen, DirectionComponent.Direction.RIGHT, Action.WALKING));
*/

public class AnimationComponent {
    private PlayScreen screen;
    private TextureAtlas atlas;
    private Action currentAction;
    private DirectionComponent.Direction currentDirection;
    private FramesComponent framesComp;

    private String regionName;
    private int regionX;
    private int regionY;

    int width;
    int height;

    Array<TextureRegion> frames;

    public AnimationComponent(PlayScreen screen, EntityType entityType, FramesComponent framesComp, String regionName) {
        this.screen = screen;
        this.framesComp = framesComp;
        this.regionName = regionName;
        this.regionX = (int) framesComp.getStillFrames(DirectionComponent.Direction.DOWN).x;
        this.regionY = (int) framesComp.getStillFrames(DirectionComponent.Direction.DOWN).x;

        // TODO: SEND THIS IN ARGUMENTS OR SETTER SO DIFFERENT PIECES CAN USE DIFFERENT DIMENSIONS
        width = 20;
        height = 24;

        currentDirection = DirectionComponent.Direction.DOWN;
        currentAction = Action.STILL;

        setAtlas(entityType);

        frames = new Array<TextureRegion>();

        // Default stance - walk-down
        frames.add(new TextureRegion(atlas.findRegion(regionName), regionX, regionY, 32, 32));
    }

    private void setAtlas(EntityType entityType) {
        switch (entityType) {
            case PLAYER:
                atlas = screen.getAssetsHandler().getPlayerAtlas();
                break;
            case ENEMY:
                atlas = screen.getAssetsHandler().getEnemyAtlas();
                break;
            case BULLET:
                atlas = screen.getAssetsHandler().getBulletAtlas();
                break;
            case ITEM:
                atlas = screen.getAssetsHandler().getItemAtlas();
                break;
        }

    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    public void setAtlas(TextureAtlas atlas) { this.atlas = atlas; }

    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }

    public void setCurrentDirection(DirectionComponent.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Array<TextureRegion> getAnimation(DirectionComponent.Direction currentDirection, Action currentAction) {
        //this.currentDirection = currentDirection;
        //this.currentAction = currentAction;

        // TODO: SOMETHING IS VERY WRONG WITH THE WALK CYCLE
        // CAUSING A HEAP ERROR, USING UP TO 1.2GB WHEN NORMAL USES 123MB
        if (currentAction == Action.WALKING) {
            frames.clear(); // clears out the default stance

            int y;
            int x;

            // TODO: I'M NOT SURE IF X SHOULD ALWAYS START AT ZERO AND MULTIPLY BY 16
            // WILL DIFFERENT ATLAS'S NEED OFFSETS?
            // TODO: NEED TO SET NUMBER OF FRAMES IN CODE
            for (int i = 0; i < 4; i++) {
                x = (int) framesComp.getWalkFrames(currentDirection, i).x;
                y = (int) framesComp.getWalkFrames(currentDirection, i).y;

                frames.add(new TextureRegion(atlas.findRegion(regionName), x, y, width, height));
            }
        } else if (currentAction == Action.ATTACK || currentAction == Action.USE) {
            frames.clear(); // clears out the default stance
            // TODO: ATTACK AND USE ARE NOT YET IMPLEMENTED
            // WILL PROBABLY USE WALKING FRAMES FOR ATTACK
            // IF A GUN IS USED, BECAUSE THE PLAYER WOULD BE
            // WALKING WITH TEH GUN
            // Action.ATTACK and Action.USE only uses a single frame
            int x = (int) framesComp.getUseFrames(currentDirection).x;
            int y = (int) framesComp.getUseFrames(currentDirection).y;
            frames.add(new TextureRegion(atlas.findRegion(regionName),
                    x, y, width, height));
        } else if (currentAction == Action.STILL) {
            frames.clear(); // clears out the default stance

            // Action.STILL only uses a single frame
            int x = (int) framesComp.getStillFrames(currentDirection).x;
            int y = (int) framesComp.getStillFrames(currentDirection).y;
            frames.add(new TextureRegion(atlas.findRegion(regionName),
                    x, y, width, height));
        }
        return frames;
    }

}
