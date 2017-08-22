package com.nilbmar.hunter.Components;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by sysgeek on 8/22/17.
 */

public class ImageComponent extends Sprite {
    private float startInWorldX;
    private float startInWorldY;

    public ImageComponent(float startInWorldX, float startInWorldY) {

        this.startInWorldX = startInWorldX;
        this.startInWorldY = startInWorldY;

        //setPosition(startInWorldX, startInWorldY);
    }

    public void draw(Batch batch) {
        super.draw(batch);
    }
}
