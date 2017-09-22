package com.nilbmar.hunter.Commands;

import com.nilbmar.hunter.Entities.NewEntity;

/**
 * Created by sysgeek on 6/10/17.
 *
 * Implementation of the Command Pattern
 */

public interface Command {
    void execute(NewEntity entity);
}
