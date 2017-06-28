package com.nilbmar.hunter.Enums;

/**
 * Created by sysgeek on 6/14/17.
 */

public enum BulletType {
    BALL("ball"), FIRE("fire");

    private String name;

    BulletType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static BulletType contains(String name) {
        for (BulletType type : BulletType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }

        return null;
    }
}