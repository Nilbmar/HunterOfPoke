package com.nilbmar.hunter.Enums;

/**
 * Created by sysgeek on 6/27/17.
 */

public enum Decorations { NONE("none"),
    FIRE("fire"), NO_COLLISION("no_collision");

    private String type;

    Decorations(String type) { this.type = type; }

    public static Decorations contains(String type) {
        for (Decorations decorations : Decorations.values()) {
            if (decorations.type.equals(type)) {
                return decorations;
            }
        }

        return null;
    }
}
