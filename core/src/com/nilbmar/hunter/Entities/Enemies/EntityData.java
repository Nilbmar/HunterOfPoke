package com.nilbmar.hunter.Entities.Enemies;

/**
 * Created by sysgeek on 6/25/17.
 */

public class EntityData {
    
    protected String name;
    protected int hitPoints;
    protected int maxHitPoints;
    protected String regionName;
    protected int regionBeginX;
    protected int regionBeginY;
    protected int regionWidth;
    protected int regionHeight;
    protected int boundsBeginX;
    protected int boundsBeginY;
    protected int boundsWidth;
    protected int boundsHeight;
    protected int offsetSpriteX;
    protected int offsetSpriteY;
    protected int acceleration;
    protected double distanceForLOS;

    public String getName() {
        return name;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getMaxHitPoints() {
        return maxHitPoints;
    }

    public String getRegionName() {
        return regionName;
    }

    public int getRegionBeginX() {
        return regionBeginX;
    }

    public int getRegionBeginY() {
        return regionBeginY;
    }

    public int getRegionWidth() {
        return regionWidth;
    }

    public int getRegionHeight() {
        return regionHeight;
    }

    public int getBoundsBeginX() {
        return boundsBeginX;
    }

    public int getBoundsBeginY() {
        return boundsBeginY;
    }

    public int getBoundsWidth() {
        return boundsWidth;
    }

    public int getBoundsHeight() {
        return boundsHeight;
    }

    public int getOffsetSpriteX() {
        return offsetSpriteX;
    }

    public int getOffsetSpriteY() {
        return offsetSpriteY;
    }

    public int getAcceleration() {
        return acceleration;
    }

    public double getDistanceForLOS() { return distanceForLOS; }
}


