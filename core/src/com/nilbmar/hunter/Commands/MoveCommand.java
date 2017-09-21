package com.nilbmar.hunter.Commands;

import com.badlogic.gdx.math.Vector2;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.NewEntity;

/**
 * Created by sysgeek on 6/11/17.
 */

public class MoveCommand implements Command {
    //Entity entity;
    private Vector2 direction = new Vector2(0, 0);

    @Override
    public void execute(NewEntity entity) {
        //this.entity = entity;
        entity.getMoveComponent().action(direction, entity.getCurrentAcceleration());
    }

    public void setMovement(Vector2 dir) {
        direction = dir;
    }
}
