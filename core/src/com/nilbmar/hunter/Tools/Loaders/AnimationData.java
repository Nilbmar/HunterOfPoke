package com.nilbmar.hunter.Tools.Loaders;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by sysgeek on 9/25/17.
 */

public class AnimationData {
    private Array<String> walkFramesArr;
    private Array<String> useFramesArr;
    private Array<String> stillFramesArr;
    private int walkFramesCount;
    private int useFramesCount;
    private int stillFramesCount;
    private int scaleSizeX;
    private int scaleSizeY;

    public Array<String> getWalkFramesArr() { return  walkFramesArr; }
    public Array<String> getUseFramesArr() { return  useFramesArr; }
    public Array<String> getStillFramesArr() { return  stillFramesArr; }

    public int getWalkFramesCount() { return walkFramesCount; }
    public int getUseFramesCount() {
        return useFramesCount;
    }
    public int getStillFramesCount() {
        return stillFramesCount;
    }

    public int getScaleSizeX() { return scaleSizeX; }
    public int getScaleSizeY() { return scaleSizeY; }

    public void setWalkFramesArr(Array<String> walkFramesArr) { this.walkFramesArr = walkFramesArr; }
    public void setUseFramesArr(Array<String> useFramesArr) { this.useFramesArr = useFramesArr; }
    public void setStillFramesArr(Array<String> stillFramesArr) { this.stillFramesArr = stillFramesArr; }
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
