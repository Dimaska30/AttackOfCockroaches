package com.dimaska.game;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by Администратор on 03.04.2017.
 */

public abstract class Subject {
    private Array<Observer> observers = new Array<Observer>();

    void addObserver(Observer observer) {
        observers.add(observer);
    }

    void deleteObject() {
        observers.clear();
        observers=null;
    }

    void notify(Cockroach cockroach, String event) {
        for(int index=0;index<observers.size;index++){
            observers.get(index).onNotify(cockroach,event);
        }
    }

    void notify(float x, float y, String event) {
        for(int index=0;index<observers.size;index++){
            observers.get(index).onNotify(x,y,event);
        }
    }
}
