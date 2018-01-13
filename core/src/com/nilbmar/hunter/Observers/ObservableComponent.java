package com.nilbmar.hunter.Observers;


import com.badlogic.gdx.utils.Array;

/**
 * Created by sysgeek on 1/12/18.
 *
 * Purpose: Make Subject into a component that can be attached
 *          so that the code is kept out of classes like Player/Enemy
 */

public class ObservableComponent implements Subject {

    private Array<Observer> observers;

    public ObservableComponent() {
        observers = new Array<Observer>();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        //int obsToDelete = observers.indexOf(observer, false);
        //observers.removeIndex(obsToDelete);

        observers.removeValue(observer, false);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
