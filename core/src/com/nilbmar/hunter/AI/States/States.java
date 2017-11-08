package com.nilbmar.hunter.AI.States;

/**
 * Created by sysgeek on 11/8/17.
 */

public interface States {
    void setAction(Action action);
    Action getAction();

    void setGoal(Goal goal);
    Goal getGoal();
}
