package com.nilbmar.hunter.Enums;

/**
 * Created by sysgeek on 6/24/17.
 *
 * Enum: EnemyType
 * Purpose: Allow B2WorldCreator to get which Enemy to spawn
 * from a Tiled map based on Custom Property
 */

public enum EnemyType { BAT("bat"), SWARM("swarm");

    private String name;
    EnemyType(String name) { this.name = name; }

    public String getName() { return name; }

    public static EnemyType contains(String name) {
        for (EnemyType type : EnemyType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }

        return null;
    }
}
