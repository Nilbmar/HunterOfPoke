package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.nilbmar.hunter.Entities.Decorators.SomeOtherDecorator;
import com.nilbmar.hunter.Entities.Decorators.WeaponDecorator;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Enemies.EntityData;
import com.nilbmar.hunter.Enums.Decorations;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Screens.PlayScreen;
import com.nilbmar.hunter.Tools.Loaders.Loader;

/**
 * Created by sysgeek on 6/27/17.
 *
 * Entities: EntityLoader
 * Purpose: Load json into an entity's variables
 * as well as decide if a Decorator should be added
 *
 * // TODO: CURRENTLY ONLY ADDS DECORATOR FOR ENEMIES
 */

public class EntityLoader implements Loader {
    private NewEntity entity;
    private String file;
    private EntityType entityType;

    public void setEntityType(EntityType entityType) { this.entityType = entityType; }

    @Override
    public void setFile(String file) { this.file = file; }

    @Override
    public void load() {
        FileHandle handle = Gdx.files.internal(file);
        String fileContent = handle.readString();
        Json json = new Json();
        EntityData data = json.fromJson(EntityData.class, fileContent);
        // TODO: MAYBE PUT BACK IN SEPARATE FUNCTION
        switch(entityType) {
            case ENEMY:
                ((Enemy) entity).getLifeComponent().setHitPoints(data.getHitPoints());
                ((Enemy) entity).getLifeComponent().setMaxHitPoints(data.getMaxHitPoints());
                break;
            case PLAYER:
                //((Player) entity).setHitPoints(data.getHitPoints());
                //((Player) entity).setMaxHitPoints(data.getMaxHitPoints());
                break;
            /*
            case ITEM:
                entity = new Item(screen, startX, startY);
                break;
            case BULLET:
                entity = new Bullet(screen, startX, startY);
                break;
            */
        }
        // TODO: GET NAME FROM DATA AND USE THAT TO SET REGION BY NAME+DIRECTION
        entity.setName(data.getName());
        entity.setRegionName(data.getRegionName());

        entity.setCurrentAcceleration(data.getAcceleration());

        entity.setRegionBeginX(data.getRegionBeginX());
        entity.setRegionBeginY(data.getRegionBeginY());
        entity.setRegionWidth(data.getRegionWidth());
        entity.setRegionHeight(data.getRegionHeight());

        entity.setBoundsBeginX(data.getBoundsBeginX());
        entity.setBoundsBeginY(data.getBoundsBeginY());
        entity.setBoundsWidth(data.getBoundsWidth());
        entity.setBoundsHeight(data.getBoundsHeight());

        entity.setOffsetSpriteX(data.getOffsetSpriteX());
        entity.setOffsetSpriteY(data.getOffsetSpriteY());

        entity.prepareToDraw();

        //return entity;
    }

    public NewEntity decorate(PlayScreen screen, float startX, float startY, String properties) {
        entity = null;
        // Custom Properties set in Tiled
        // Full Properties contain: Decorations + ":" + BulletProperties
        // Colon is used to separate the decorations from the bullet properties
        Array<String> fullProperties = new Array<String>(properties.split(":"));

        // Decorations contain all Decorations listed in Tiled custom property
        // separated by a blank space
        Array<String> arrDecorations = new Array<String>(fullProperties.get(0).split(" "));

        Decorations dec;

        // If Tiled spawn point has custom property that contains a Decoration
        // create that type of Enemy, otherwise set a plain entity
        // will later on make decorations for other entity types besides enemies
        for (String str : arrDecorations) {
            // Check if Decorations enum has one with a value equal to str
            dec = Decorations.contains(str);
            if (dec == null) {
                // Make sure dec is set even if the custom property
                // uses the wrong string or no string
                dec = Decorations.NONE;
            }

            // Add decorators
            switch(dec) {
                case FIRE:
                    entity = new WeaponDecorator(screen, startX, startY, fullProperties);
                    break;
                case NO_COLLISION: // TODO: THIS IS CURRENTLY STILL ADDING A FIRECOMMAND
                    entity = new SomeOtherDecorator(screen, startX, startY);
                    break;
                case NONE:
                    setPlainEntity(screen, startX, startY);
                    break;
                default:
                    break;
            }
        }

        if (entity != null) {
            load();
        }

        return entity;
    }

    private void setPlainEntity(PlayScreen screen, float startX, float startY) {
        switch (entityType) {
            case ENEMY:
                entity = new Enemy(screen, startX, startY);
                break;
            case PLAYER:
                //entity = new Player(screen, startX, startY);
                break;
        }
    }
}
