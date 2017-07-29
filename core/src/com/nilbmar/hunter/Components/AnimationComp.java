package com.nilbmar.hunter.Components;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Enums.Action;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 7/28/17.
 */

public class AnimationComp {
    private PlayScreen screen;
    private Entity entity;
    private TextureAtlas atlas;
    private Action currentAction;
    private DirectionComponent.Direction currentDirection;
    private FramesComponent framesComp;

    private String regionName;
    private int regionX;
    private int regionY;

    private int width;
    private int height;

    private Array<TextureRegion> frames;

    private Array<Animation> animations;
    private Animation charStill;
    private Animation charWalkUp;
    private Animation charWalkUpLeft;
    private Animation charWalkUpRight;
    private Animation charWalkDown;
    private Animation charWalkDownLeft;
    private Animation charWalkDownRight;
    private Animation charWalkLeftRight;

    public AnimationComp(PlayScreen screen, Entity entity, FramesComponent framesComp, String regionName) {
        this.screen = screen;
        this.entity = entity;
        this.framesComp = framesComp;
        this.regionName = regionName;
        this.regionX = (int) framesComp.getStillFrames(DirectionComponent.Direction.DOWN).x;
        this.regionY = (int) framesComp.getStillFrames(DirectionComponent.Direction.DOWN).x;

        // TODO: SEND THIS IN ARGUMENTS OR SETTER SO DIFFERENT PIECES CAN USE DIFFERENT DIMENSIONS
        width = entity.getImageWidth();
        height = entity.getImageHeight();

        currentDirection = DirectionComponent.Direction.DOWN;
        currentAction = Action.STILL;

        setAtlas(entity.getEntityType());

        frames = new Array<TextureRegion>();

        // Default stance - walk-down
        // TODO: DON'T REMEMBER WHERE 32/32 COMES FROM
        // PROBABLY FRAME SIZES, BUT NEED TO PASS INTO THIS FROM JSON
        //frames.add(new TextureRegion(atlas.findRegion(regionName), regionX, regionY, 32, 32));

        animations = new Array<Animation>();
        setAnimations();
    }

    // TODO: WHY DO I HAVE TWO setAtlas() functions?
    public void setAtlas(TextureAtlas atlas) { this.atlas = atlas; }
    private void setAtlas(EntityType entityType) {
        // Get a TextureAtlas from the AssetHandler
        // based on the type of entity to which this is attached
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


    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }

    public void setCurrentDirection(DirectionComponent.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Array<TextureRegion> getAnimationArray(DirectionComponent.Direction direction, Action action) {
        //int y;
        //int x;

        switch(action) {
            case WALKING:
                frames.clear(); // clears out the default stance

                // TODO: I'M NOT SURE IF X SHOULD ALWAYS START AT ZERO AND MULTIPLY BY 16
                // WILL DIFFERENT ATLAS'S NEED OFFSETS?
                // TODO: NEED TO SET NUMBER OF FRAMES IN CODE
            {
                int x;
                int y;

                for (int i = 0; i < 4; i++) {
                    x = (int) framesComp.getWalkFrames(direction, i).x;
                    y = (int) framesComp.getWalkFrames(direction, i).y;

                    frames.add(new TextureRegion(atlas.findRegion(regionName), x, y, width, height));
                }
            }
                break;

            case STILL:
                frames.clear(); // clears out the default stance

            {
                int x;
                int y;

                // Action.STILL only uses a single frame
                x = (int) framesComp.getStillFrames(direction).x;
                y = (int) framesComp.getStillFrames(direction).y;
                frames.add(new TextureRegion(atlas.findRegion(regionName),
                        x, y, width, height));
            }
                break;

            case USE:
            case ATTACK:
                frames.clear(); // clears out the default stance
                // TODO: ATTACK AND USE ARE NOT YET IMPLEMENTED
                // WILL PROBABLY USE WALKING FRAMES FOR ATTACK
                // IF A GUN IS USED, BECAUSE THE PLAYER WOULD BE
                // WALKING WITH TEH GUN
                // Action.ATTACK and Action.USE only uses a single frame
            {
                int x;
                int y;

                x = (int) framesComp.getUseFrames(direction).x;
                y = (int) framesComp.getUseFrames(direction).y;
                frames.add(new TextureRegion(atlas.findRegion(regionName),
                        x, y, width, height));
            }
                break;
        }

        return frames;
    }

    public Animation makeTexturesIntoAnimation(float animSpeed, DirectionComponent.Direction direction, Action action) {
        return new Animation<TextureRegion>(animSpeed, getAnimationArray(direction, action));
    }
    
    private void setAnimations() {
        charStill = makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.DOWN, Action.STILL);
        /*
        charWalkUp = makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.UP, Action.WALKING);
        charWalkUpLeft = makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.UP_LEFT, Action.WALKING);
        charWalkUpRight = makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.UP_RIGHT, Action.WALKING);
        
        charWalkDown = makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.DOWN, Action.WALKING);
        charWalkDownLeft = makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.DOWN_LEFT, Action.WALKING);
        charWalkDownRight = makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.DOWN_RIGHT, Action.WALKING);
        
        charWalkLeftRight = makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.LEFT, Action.WALKING);
        */

        animations.add(makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.UP, Action.WALKING));
        animations.add(makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.UP_LEFT, Action.WALKING));
        animations.add(makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.UP_RIGHT, Action.WALKING));

        animations.add(makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.DOWN, Action.WALKING));
        animations.add(makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.DOWN_LEFT, Action.WALKING));
        animations.add(makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.DOWN_RIGHT, Action.WALKING));

        animations.add(makeTexturesIntoAnimation(0.1f, DirectionComponent.Direction.LEFT, Action.WALKING));
    }

    public Animation getStill(DirectionComponent.Direction direction) {
        // direction is unused, but may be added in later
        return charStill;
    }

    public Animation getWalk(DirectionComponent.Direction direction, Action action) {
        int index = 0;
        switch(direction) {
            case UP:
                index = 0;
                break;
            case UP_LEFT:
                index = 1;
                break;
            case UP_RIGHT:
                index = 2;
                break;
            case DOWN:
                index = 3;
                break;
            case DOWN_LEFT:
                index = 4;
                break;
            case DOWN_RIGHT:
                index = 5;
                break;
            case LEFT:
            case RIGHT:
                index = 6;
                break;
        }

        return animations.get(index);
    }
}
