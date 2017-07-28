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
        frames.add(new TextureRegion(atlas.findRegion(regionName), regionX, regionY, 32, 32));

    }

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
    public void setAtlas(TextureAtlas atlas) { this.atlas = atlas; }

    public void setCurrentAction(Action currentAction) {
        this.currentAction = currentAction;
    }

    public void setCurrentDirection(DirectionComponent.Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public Array<TextureRegion> getAnimationArray() {
        int y;
        int x;

        switch(currentAction) {
            case WALKING:
                frames.clear(); // clears out the default stance

                // TODO: I'M NOT SURE IF X SHOULD ALWAYS START AT ZERO AND MULTIPLY BY 16
                // WILL DIFFERENT ATLAS'S NEED OFFSETS?
                // TODO: NEED TO SET NUMBER OF FRAMES IN CODE
                for (int i = 0; i < 4; i++) {
                    x = (int) framesComp.getWalkFrames(currentDirection, i).x;
                    y = (int) framesComp.getWalkFrames(currentDirection, i).y;

                    frames.add(new TextureRegion(atlas.findRegion(regionName), x, y, width, height));
                }
                break;

            case STILL:
                frames.clear(); // clears out the default stance

                // Action.STILL only uses a single frame
                x = (int) framesComp.getStillFrames(currentDirection).x;
                y = (int) framesComp.getStillFrames(currentDirection).y;
                frames.add(new TextureRegion(atlas.findRegion(regionName),
                        x, y, width, height));
                break;

            case USE:
            case ATTACK:
                frames.clear(); // clears out the default stance
                // TODO: ATTACK AND USE ARE NOT YET IMPLEMENTED
                // WILL PROBABLY USE WALKING FRAMES FOR ATTACK
                // IF A GUN IS USED, BECAUSE THE PLAYER WOULD BE
                // WALKING WITH TEH GUN
                // Action.ATTACK and Action.USE only uses a single frame
                x = (int) framesComp.getUseFrames(currentDirection).x;
                y = (int) framesComp.getUseFrames(currentDirection).y;
                frames.add(new TextureRegion(atlas.findRegion(regionName),
                        x, y, width, height));
                break;
        }

        return frames;
    }

    public Animation getAnimation(float animSpeed, DirectionComponent.Direction currentDirection, Action currentAction) {
        this.currentDirection = currentDirection;
        this.currentAction = currentAction;
        return new Animation(animSpeed, getAnimationArray());
    }
}
