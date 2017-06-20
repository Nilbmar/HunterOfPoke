package com.nilbmar.hunter.Tools.Enums;

/**
 * Created by sysgeek on 6/13/17.
 */

public enum ItemType {
    ACCELERATION("acceleration"), DEATH("death");

    private String name;

    ItemType(String name) { this.name = name; }


    public String getName() {
        return name;
    }
}
