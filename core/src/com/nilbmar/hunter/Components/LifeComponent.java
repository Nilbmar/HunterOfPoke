package com.nilbmar.hunter.Components;

/**
 * Created by sysgeek on 9/24/17.
 */

public class LifeComponent implements Component {

    private int hitPoints;
    private int maxHitPoints;
    private boolean zeroHitPoints = false;

    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
    public void setMaxHitPoints(int maxHitPoints) { this.maxHitPoints = maxHitPoints; }

    public int getHitPoints() { return hitPoints; }
    public int getMaxHitPoints() { return maxHitPoints; }
    public boolean isDead() { return zeroHitPoints; }

    public void loseHitPoints(int hitPointsToLose) {
        if (hitPointsToLose >= hitPoints) {
            hitPoints = 0;
            zeroHitPoints = true;
        } else {
            hitPoints -= hitPointsToLose;
        }
    }

    public void recoverHitPoints(int hitPointsToAdd) {
        int tempHP = hitPoints + hitPointsToAdd;
        if (tempHP >= maxHitPoints) {
            hitPoints = maxHitPoints;
        } else {
            hitPoints = tempHP;
        }
    }
}
