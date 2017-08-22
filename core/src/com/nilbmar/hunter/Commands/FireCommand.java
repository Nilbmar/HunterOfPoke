package com.nilbmar.hunter.Commands;

import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.NewEntity;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Enums.EntityType;
import com.nilbmar.hunter.Tools.BoxPatternHandler;
import com.nilbmar.hunter.Tools.BulletPatternHandler;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;

/**
 * Created by sysgeek on 6/10/17.
 */

public class FireCommand implements Command {
    // TODO: REMOVE BULLETS
    private BulletPatternHandler bulletPatterns;
    private BoxPatternHandler boxPatterns;
    private BulletType type;
    private ShotType shot;

    public FireCommand(BulletPatternHandler bulletPatterns, BulletType type, ShotType shot) {
        //this.entity = entity;
        this.bulletPatterns = bulletPatterns;
        this.type = type;
        this.shot = shot;
    }

    public FireCommand(BoxPatternHandler boxPatterns, BulletType type, ShotType shot) {
        //this.entity = entity;
        this.boxPatterns = boxPatterns;
        this.type = type;
        this.shot = shot;
    }

    public void setType(BulletType type) { this.type = type; }


    public void boxExecute(Entity entity) {
        boolean fire = false;
        DirectionComponent.Direction direction = DirectionComponent.Direction.DOWN;

        switch (entity.getEntityType()) {
            case PLAYER:
                direction = ((Player) entity).getDirectionComponent().getDirection();
                fire = true;
                break;
            case ENEMY:
                direction = ((Enemy) entity).getDirectionComponent().getDirection();
                fire = true;
                break;
        }
        if (fire) {
            switch (shot) {
                case TWIN:
                    boxPatterns.twinShot(type, direction, entity.getSpawnOtherX(), entity.getSpawnOtherY());
                    break;
                case SINGLE:
                    boxPatterns.singleShot(type, direction, entity.getSpawnOtherX(), entity.getSpawnOtherY());
                    break;
            }
        }
    }

    @Override
    public void execute(Entity entity) {
        boolean fire = false;
        DirectionComponent.Direction direction = DirectionComponent.Direction.DOWN;

        switch (entity.getEntityType()) {
            case PLAYER:
                direction = ((Player) entity).getDirectionComponent().getDirection();
                fire = true;
                break;
            case ENEMY:
                direction = ((Enemy) entity).getDirectionComponent().getDirection();
                fire = true;
                break;
        }
        if (fire) {
            switch (shot) {
                case TWIN:
                    bulletPatterns.twinShot(type, direction, entity.getSpawnOtherX(), entity.getSpawnOtherY());
                    break;
                case SINGLE:
                    bulletPatterns.singleShot(type, direction, entity.getSpawnOtherX(), entity.getSpawnOtherY());
                    break;
            }
        }
    }
}
