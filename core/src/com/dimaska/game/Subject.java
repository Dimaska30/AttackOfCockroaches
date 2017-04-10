package com.dimaska.game;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by Администратор on 03.04.2017.
 */

public abstract class Subject {
    public Array<Observer> observers=new Array<Observer>();

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void removeObserver(Observer observer){
        int index=observers.indexOf(observer,true);
        observers.removeIndex(index);
    }

    public void deleteObject(){
        observers.clear();
        observers=null;
    }

    public void notify(Cockroach cockroach,String event){
        for(int index=0;index<observers.size;index++){
            observers.get(index).onNotify(cockroach,event);
        }
    }

    public void notify(float x,float y,String event){
        for(int index=0;index<observers.size;index++){
            observers.get(index).onNotify(x,y,event);
        }
    }
}
