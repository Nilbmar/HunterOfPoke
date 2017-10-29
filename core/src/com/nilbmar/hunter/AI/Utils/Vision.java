package com.nilbmar.hunter.AI.Utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.nilbmar.hunter.Entities.Entity;
import com.nilbmar.hunter.Screens.PlayScreen;

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
    private PlayScreen screen;
    private Entity entity;

    private ArrayList<Vector2> points;
    private Vector2 target;
    private Vector2 origin;

    private RayCastCallback raycastCallback;
    private Vector2 rayCollision = new Vector2();
    private Vector2 rayNormal = new Vector2();
    private Boolean hasLoS = false;

    public Vision(PlayScreen screen) {
        this.screen = screen;
    }

    public void setupRaycast(Entity entity) {
        this.entity = entity;
        // Used for finding Line of Sight to player
        raycastCallback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                rayCollision.set(point);
                rayNormal.set(normal).add(point); // Have to add because normal is relative to the point

                if (fixture == Vision.this.entity.getB2Body().getFixtureList().first()) {
                    hasLoS = true;
                } else {
                    hasLoS = false;
                }
                return fraction;
            }
        };
    }

    public boolean hasLoS(Entity source, Entity target) {
        // Checks a ray cast for LoS
        hasLoS = false;
        screen.getWorld().rayCast(raycastCallback, source.getPosition(), target.getPosition());

        return hasLoS;
    }

    public double getDistance(Vector2 firstPoint, Vector2 secondPoint) {
        double distance = 0.0f;
        target = firstPoint;
        origin = secondPoint;

        distance = ((secondPoint.x - firstPoint.x) * (secondPoint.x - firstPoint.x))
                + ((secondPoint.y - firstPoint.y) * (secondPoint.y - firstPoint.y));

        return Math.sqrt(distance);
    }

    public ArrayList<Vector2> getPoints() { return points; }

    @Override
    public Iterator<Vector2> iterator() {
        return points.iterator();
    }
}
