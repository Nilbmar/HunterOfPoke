package com.nilbmar.hunter.Commands;

import com.nilbmar.hunter.Components.DirectionComponent;
import com.nilbmar.hunter.Entities.Enemies.Enemy;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Player;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;
import com.nilbmar.hunter.Tools.BoxPatternHandler;

/**
 * Created by sysgeek on 6/10/17.
 */

public class FireBoxCommand implements Command {
    private BoxPatternHandler boxPatterns;
    private BulletType type;
    private ShotType shot;

    public FireBoxCommand(BoxPatternHandler boxPatterns, BulletType type, ShotType shot) {
        //this.entity = entity;
        this.boxPatterns = boxPatterns;
        this.type = type;
        this.shot = shot;
    }

    public void setType(BulletType type) { this.type = type; }

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
                    boxPatterns.twinShot(type, direction, entity.getSpawnOtherX(), entity.getSpawnOtherY());
                    break;
                case SINGLE:
                    boxPatterns.singleShot(type, direction, entity.getSpawnOtherX(), entity.getSpawnOtherY());
                    break;
            }
        }
    }
}
