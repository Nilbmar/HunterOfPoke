package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.nilbmar.hunter.Entities.Decorators.WeaponDecorator;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Enemies.EntityData;
import com.nilbmar.hunter.Enums.Decorations;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/27/17.
 *
 * Entities: EntityLoader
 * Purpose: Load json into an entity's variables
 * as well as decide if a Decorator should be added
 *
 * // TODO: CURRENTLY ONLY ADDS DECORATOR FOR ENEMIES
 */

public class EntityLoader {
    private Entity entity;
    private String file;
    private EntityType entityType;

    public void setEntityType(EntityType entityType) { this.entityType = entityType; }
    public void setFile(String file) { this.file = file; }


    public void loadJson() {
        FileHandle handle = Gdx.files.internal(file);
        String fileContent = handle.readString();
        Json json = new Json();
        EntityData data = json.fromJson(EntityData.class, fileContent);
        // TODO: MAYBE PUT BACK IN SEPARATE FUNCTION
        switch(entityType) {
            case ENEMY:

                ((Enemy) entity).setHitPoints(data.getHitPoints());
                ((Enemy) entity).setMaxHitPoints(data.getMaxHitPoints());
                break;
            case PLAYER:

                ((Player) entity).setHitPoints(data.getHitPoints());
                ((Player) entity).setMaxHitPoints(data.getMaxHitPoints());
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

    public Entity decorate(PlayScreen screen, float startX, float startY, String decorations) {
        entity = null;
        Array<String> arrDecorations = new Array<String>(decorations.split(" "));
        Decorations dec;

        // TODO: BE ABLE TO TACK ON _SINGLE OR _TWIN etc FOR FIRE
        // Possibly use a second string split?

        // If Tiled spawn point has custom property that contains a Decoration
        // create that type of Enemy, otherwise set a plain entity
        // will later on make decorations for other entity types besides enemies
        for (String str : arrDecorations) {
            dec = Decorations.contains(str);
            if (dec == null) {
                dec = Decorations.NONE;
            }

            switch(dec) {
                case FIRE:
                    entity = new WeaponDecorator(screen, startX, startY);
                    loadJson();
                    break;
                case NO_COLLISION:

                    break;
                case NONE:
                    setPlainEntity(screen, startX, startY);
                    break;
                default:
                    break;
            }
        }

        return entity;
    }

    private void setPlainEntity(PlayScreen screen, float startX, float startY) {
        switch (entityType) {
            case ENEMY:
                entity = new Enemy(screen, startX, startY);
                loadJson();
                break;
            case PLAYER:
                entity = new Player(screen, startX, startY);
                loadJson();
                break;
        }
    }
}
