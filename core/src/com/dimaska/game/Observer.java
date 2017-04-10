package com.dimaska.game;

/**
 * Created by Администратор on 03.04.2017.
 */

public interface Observer {
    void onNotify(Cockroach cockroach,String event);
    void onNotify(float x,float y,String event);
}
