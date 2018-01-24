package com.nilbmar.hunter.Observers;

import com.nilbmar.hunter.Scenes.Hud;

/**
 * Created by sysgeek on 1/12/18.
 *
 * Purpose: Interface for Observers
 */

public interface Observer {
    void update();
    String getType();
}
