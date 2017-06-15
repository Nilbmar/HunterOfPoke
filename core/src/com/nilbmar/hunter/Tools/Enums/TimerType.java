package com.nilbmar.hunter.Tools.Enums;

/**
 * Created by sysgeek on 6/13/17.
 */

public enum TimerType {
    ACCELERATION("acceleration"), DEATH("death");

    private String name;

    TimerType(String name) { this.name = name; }


    public String getName() {
        return name;
    }
}
