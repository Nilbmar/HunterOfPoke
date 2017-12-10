package com.nilbmar.hunter.Components;

import com.nilbmar.hunter.Commands.Command;
import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Tools.BoxPatternHandler;
import com.nilbmar.hunter.Tools.BulletPatternHandler;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;

/**
 * Created by sysgeek on 6/10/17.
 *
 * Purpose: Add a weapon to Player or Enemy entities
 */

public class WeaponComponent implements Component {
    private BulletPatternHandler bulletPatterns;
    private BoxPatternHandler boxPatterns;
    private BulletType type;
    private ShotType shot;
    //private String firedBy;

    public WeaponComponent(BulletPatternHandler bulletPatterns, BulletType type, ShotType shot) {
        this.bulletPatterns = bulletPatterns;
        this.type = type;
        this.shot = shot;
        //firedBy = null;
    }

    public void setType(BulletType type) { this.type = type; }


    public void fire(Entity entity) {
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
                    bulletPatterns.twinShot(type, direction, entity.getSpawnOtherX(), entity.getSpawnOtherY(), entity.getEntityType().toString());
                    break;
                case SINGLE:
                    bulletPatterns.singleShot(type, direction, entity.getSpawnOtherX(), entity.getSpawnOtherY(), entity.getEntityType().toString());
                    break;
            }
        }
    }
}
