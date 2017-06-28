package com.nilbmar.hunter.Enums;

/**
 * Created by sysgeek on 6/10/17.
 */

public enum ShotType {
    SINGLE("single"), TWIN("twin");

    private String name;
    ShotType(String name) { this.name = name; }

    public String getName() { return name; }

    public static ShotType contains(String name) {
        for (ShotType type : ShotType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }

        return null;
    }
}