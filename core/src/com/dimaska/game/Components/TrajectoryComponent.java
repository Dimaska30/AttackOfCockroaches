package com.dimaska.game.Components;

/**
 * Created by dimaska on 06.03.17.
 */

public class TrajectoryComponent {
    private float vx,vy;

    public TrajectoryComponent(float vx, float vy){
        this.vx=vx;
        this.vy=vy;
    }

    public float getX(float deltaTime,float x){
        return x+vx*deltaTime;
    }

    public float getY(float deltaTime,float y){
        return y+vy*deltaTime;
    }
}