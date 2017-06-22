package com.nilbmar.hunter.Enums;

/**
 * Created by sysgeek on 4/1/17.
 */

public enum Borders {
    LEFT(1.2757324f), RIGHT(8.948857f),
    TOP(9.262899f), BOTTOM(0.98901355f);

    private float num;

    Borders(float num) {
        this.num = num;
    }

    public float value() {
        return num;
    }
}
