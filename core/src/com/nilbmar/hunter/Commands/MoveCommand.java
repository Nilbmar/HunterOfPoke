package com.nilbmar.hunter.Commands;

import com.badlogic.gdx.math.Vector2;
import com.nilbmar.hunter.Entities.Entity;

/**
 * Created by sysgeek on 6/11/17.
 */

public class MoveCommand implements Command {
    //Entity entity;
    private Vector2 direction = new Vector2(0, 0);

    @Override
    public void execute(Entity entity) {
        //this.entity = entity;
        entity.getMoveComponent().action(direction, entity.getCurrentAcceleration());
    }

    public void setMovement(Vector2 dir) {
        direction = dir;
    }
}
