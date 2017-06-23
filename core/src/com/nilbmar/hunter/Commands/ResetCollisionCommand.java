package com.nilbmar.hunter.Commands;

import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.Player;

/**
 * Created by sysgeek on 6/22/17.
 */

public class ResetCollisionCommand implements Command {


    @Override
    public void execute(Entity entity) {
        // boolean collideWithEnemies
        ((Player) entity).resetCollision(false);
    }

    public void undo(Entity entity) {
        ((Player) entity).resetCollision(true);
    }
}
