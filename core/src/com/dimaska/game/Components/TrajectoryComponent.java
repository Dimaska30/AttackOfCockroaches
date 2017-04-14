package com.dimaska.game.Components;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.dimaska.game.Cockroach;
import com.dimaska.game.GameConst;

/**
 * Created by dimaska on 06.03.17.
 */

public class TrajectoryComponent {
    private float vx,vy;
    private float ax,ay;
    private float maxVx,maxVy;


    public TrajectoryComponent(float vx, float vy,float maxVx,float maxVy){
        this.vx=vx;
        this.vy=vy;
        this.ax=(float)(Math.random()*20 -10);
        this.ay=-(float)(Math.random()*10+10);
        this.maxVx=maxVx;
        this.maxVy=maxVy;
    }

    public void update(float deltaTime,float x){
        if(vx<maxVx) {
            vx += ax *deltaTime;
        }else{
            vx=maxVx;
        }
        if(x> GameConst.X || x<0){
            vx=-vx;
            ax=-ax;
        }
        if(vy<maxVy){
            vy+=ay*deltaTime;
        }else{
            vy=maxVy;
        }
    }

    public float getX(float deltaTime,float x){
        return x+vx*deltaTime;
    }

    public float getY(float deltaTime,float y){
        return y+vy*deltaTime;
    }
}