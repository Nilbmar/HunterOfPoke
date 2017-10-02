package com.nilbmar.hunter.Entities.Enemies;

/**
 * Created by sysgeek on 9/27/17.
 *
 * Purpose: enum to use in switch cases determining Enemy type
 */

public enum EnemyType {
    SWARM("swarm"), SPIDER("spider"),
    GOO("goo"), GOOBABY("goobaby"), SMUSHROOM("smushroom"),
    BAT("bat"), BATEYE("bateye"), FLOATEYE("floateye"), WHELP("whelp"),
    HORNS("horns"), LOCKS("locks"),
    NOTLINK("notlink"), KULTIST("kultist"), CLOAK("cloak"), FEATHER("feather"),
    FANG("fang"), FANGHOOD("fanghood"),
    ARMORLANCER("armorlancer"),
    BONES("bones"), BONESBLADE("bonesblade"),
    BEASTNORMAL("beastnormal"), BEASTAXE("beastaxe"), BEASTSWORD("beastsword"), BEASTMACE("beastmace");

    private String regionNamePrefix;
    EnemyType(String regionNamePrefix) { this.regionNamePrefix = regionNamePrefix; }
    //BulletType(String name) { this.name = name; }

    public String getName() {
        return regionNamePrefix;
    }

    public static EnemyType contains(String regionNamePrefix) {
        for (EnemyType type : EnemyType.values()) {
            if (type.getName().equals(regionNamePrefix)) {
                return type;
            }
        }

        return null;
    }
}
