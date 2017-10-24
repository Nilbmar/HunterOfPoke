package com.nilbmar.hunter.AI.Utils;

import com.nilbmar.hunter.Entities.NewEntity;


/**
 * Created by sysgeek on 10/23/17.
 */

public class FieldOfView {
    private NewEntity target;
    private NewEntity source;

    public FieldOfView() {

    }

    public void setTarget(NewEntity target) { this.target = target; }
    public void setSource(NewEntity source) { this.source = source; }

}
