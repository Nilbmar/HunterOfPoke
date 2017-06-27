package com.nilbmar.hunter.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.nilbmar.hunter.Entities.Decorators.WeaponDecorator;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Enemies.EntityData;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Screens.PlayScreen;

/**
 * Created by sysgeek on 6/27/17.
 */

public class EntityLoaderBasic {
    // TODO: RENAME
    Entity entity;
    String file;
    EntityType entityType;

    public void setEntityType(EntityType entityType) { this.entityType = entityType; }
    public void setFile(String file) { this.file = file; }


    public void load() {
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

        entity.finalize();

        //return entity;
    }

    public Entity decorate(PlayScreen screen, float startX, float startY, String decorations) {
        entity = null;
        switch(1) {
            case 1:
                entity = new WeaponDecorator(screen, startX, startY);
                load();
            break;
        }
        return entity;
    }
}
