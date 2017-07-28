package com.nilbmar.hunter.Components;

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

    int width;
    int height;

    Array<TextureRegion> frames;

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
}
