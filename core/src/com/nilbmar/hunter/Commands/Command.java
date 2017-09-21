package com.nilbmar.hunter.Commands;

import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Entities.NewEntity;

/**
 * Created by sysgeek on 6/10/17.
 *
 * Implementation of the Command Pattern
 */

public interface Command {
    //public void execute(Entity entity);
    public void execute(NewEntity entity);
}
