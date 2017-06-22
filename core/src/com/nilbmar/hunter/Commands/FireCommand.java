package com.nilbmar.hunter.Commands;

import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Tools.BulletPatternHandler;
import com.nilbmar.hunter.Enums.BulletType;
import com.nilbmar.hunter.Enums.ShotType;

/**
 * Created by sysgeek on 6/10/17.
 */

public class FireCommand implements Command {
    private BulletPatternHandler bulletPatterns;
    private BulletType type;
    private ShotType shot;

    public FireCommand(BulletPatternHandler bulletPatterns, BulletType type, ShotType shot) {
        //this.entity = entity;
        this.bulletPatterns = bulletPatterns;
        this.type = type;
        this.shot = shot;
    }

    public void setType(BulletType type) { this.type = type; }

    @Override
    public void execute(Entity entity) {
        switch (shot) {
            case TWIN:
                bulletPatterns.twinShot(type, entity.getDirection(), entity.getSpawnOtherX(), entity.getSpawnOtherY());
                break;
            case SINGLE:
                bulletPatterns.singleShot(type, entity.getDirection(), entity.getSpawnOtherX(), entity.getSpawnOtherY());
                break;
        }
    }
}
