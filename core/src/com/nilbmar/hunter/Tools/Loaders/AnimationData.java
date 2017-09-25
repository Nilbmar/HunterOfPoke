package com.nilbmar.hunter.Tools.Loaders;

/**
 * Created by sysgeek on 9/25/17.
 */

public class AnimationData {
    private int walkFramesCount;
    private int useFramesCount;
    private int stillFramesCount;
    private int scaleSizeX;
    private int scaleSizeY;

    public int getWalkFramesCount() {
        return walkFramesCount;
    }
    public int getUseFramesCount() {
        return useFramesCount;
    }
    public int getStillFramesCount() {
        return stillFramesCount;
    }
    public int getScaleSizeX() { return scaleSizeX; }
    public int getScaleSizeY() { return scaleSizeY; }

    public void setWalkFramesCount(int walkFramesCount) {
        this.walkFramesCount = walkFramesCount;
    }
    public void setUseFramesCount(int useFramesCount) {
        this.useFramesCount = useFramesCount;
    }
    public void setStillFramesCount(int stillFramesCount) { this.stillFramesCount = stillFramesCount; }
    public void setScaleSizeX(int scaleSizeX) { this.scaleSizeX = scaleSizeX; }
    public void setScaleSizeY(int scaleSizeY) { this.scaleSizeY = scaleSizeY; }
}
