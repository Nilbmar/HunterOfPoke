package com.nilbmar.hunter.AI.Utils;

import com.nilbmar.hunter.Entities.Entity;


/**
 * Created by sysgeek on 10/23/17.
 */

public class FieldOfView {
    private Entity target;
    private Entity source;

    public FieldOfView() {

    }

    public void setTarget(Entity target) { this.target = target; }
    public void setSource(Entity source) { this.source = source; }

}
