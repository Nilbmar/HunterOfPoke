package com.nilbmar.hunter.Components;

import com.badlogic.gdx.Gdx;
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
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    // Allows for switching atlas during runtime:
    // Player can change skins
    public void setAtlas(TextureAtlas atlas) { this.atlas = atlas; }

    // Set atlas in constructor for this class
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

    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }

    public void setCurrentDirection(DirectionComponent.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Array<TextureRegion> getAnimationArray(DirectionComponent.Direction direction, Action action) {
        int y;
        int x;

        switch(action) {
            case WALKING:
                frames.clear(); // clears out the default stance

                // TODO: I'M NOT SURE IF X SHOULD ALWAYS START AT ZERO AND MULTIPLY BY 16
                // WILL DIFFERENT ATLAS'S NEED OFFSETS?
                // TODO: NEED TO SET NUMBER OF FRAMES IN CODE
                for (int i = 0; i < 4; i++) {
                    x = (int) framesComp.getWalkFrames(direction, i).x;
                    y = (int) framesComp.getWalkFrames(direction, i).y;

                    frames.add(new TextureRegion(atlas.findRegion(regionName), x, y, width, height));
                }

                break;

            case STILL:
                frames.clear(); // clears out the default stance

                // Action.STILL only uses a single frame
                x = (int) framesComp.getStillFrames(direction).x;
                y = (int) framesComp.getStillFrames(direction).y;
                frames.add(new TextureRegion(atlas.findRegion(regionName),
                        x, y, width, height));

                break;

            case ATTACK:
            case USE:
                frames.clear(); // clears out the default stance
                // TODO: ATTACK AND USE ARE NOT YET IMPLEMENTED
                // WILL PROBABLY USE WALKING FRAMES FOR ATTACK
                // IF A GUN IS USED, BECAUSE THE PLAYER WOULD BE
                // WALKING WITH TEH GUN
                // Action.ATTACK and Action.USE only uses a single frame
                for (int i = 0; i < 4; i++) {
                    x = (int) framesComp.getUseFrames(direction, i).x;
                    y = (int) framesComp.getUseFrames(direction, i).y;
                    frames.add(new TextureRegion(atlas.findRegion(regionName),
                            x, y, width, height));
                }
                break;
        }

        return frames;
    }

    public Animation<TextureRegion> makeTexturesIntoAnimation(float animSpeed, DirectionComponent.Direction direction, Action action) {
        return new Animation<TextureRegion>(animSpeed, getAnimationArray(direction, action));
    }

}
