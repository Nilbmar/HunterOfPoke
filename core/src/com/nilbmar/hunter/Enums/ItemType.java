package com.nilbmar.hunter.Enums;

/**
 * Created by sysgeek on 6/13/17.
 */

public enum ItemType {
    ACCELERATION("acceleration"), DEATH("death"),
    REMOVE_COLLISION("remove collision"), RESET_COLLISION("reset collision"),
    INVINCIBILITY("invincibility");

    private String name;

    ItemType(String name) { this.name = name; }


    public String getName() {
        return name;
    }

    public static ItemType contains(String name) {
        for (ItemType type : ItemType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
