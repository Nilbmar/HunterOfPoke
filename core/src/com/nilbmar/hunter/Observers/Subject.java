package com.nilbmar.hunter.Observers;

import com.nilbmar.hunter.Observers.Observer;

/**
 * Created by sysgeek on 1/12/18.
 *
 * Purpose: Interface for observers
 */

public interface Subject {

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver();
}
