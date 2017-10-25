package com.nilbmar.hunter.AI.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by sysgeek on 10/22/17.
 *
 * AI Util
 *
 * Purpose: Create a line that can be used
 * in determining Line of Sight
 */

public class Vision implements Iterable<Vector2> {
    private ArrayList<Vector2> points;
    private Vector2 target;
    private Vector2 origin;

    public Vision() {

    }

    public double getDistance(float x0, float y0, float x1, float y1) {
        double distance = 0.0f;
        target = new Vector2(x0, y0);
        origin = new Vector2(x1, y1);

        distance = ((x1 - x0) * (x1 - x0)) + ((y1 - y0) * (y1 - y0));

        return Math.sqrt(distance);
    }

    public ArrayList<Vector2> getPoints() { return points; }

    @Override
    public Iterator<Vector2> iterator() {
        return points.iterator();
    }
}
