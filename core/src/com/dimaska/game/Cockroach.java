package com.dimaska.game;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.dimaska.game.Components.BodyComponent;
import com.dimaska.game.Components.GraphicComponent;
import com.dimaska.game.Components.PowerComponent;
import com.dimaska.game.Components.TrajectoryComponent;

/**
 * Created by dimaska on 08.02.17.
 */

public class Cockroach {
    private float x,y;
    private int height,width;
    private float alpha;
    private GraphicComponent graphicComponent;

    private BodyComponent bodyComponent;
    public DefaultStateMachine<Cockroach,State<Cockroach>> stateMachine;
    PowerComponent powerComponent;
    private TrajectoryComponent trajectoryComponent;
    private float tempDeltaTime;

    public Cockroach(float x, float y, int width, int height,
                     GraphicComponent graphicComponent,
                     BodyComponent bodyComponent,
                     PowerComponent powerComponent,
                     TrajectoryComponent trajectoryComponent,
                     State<Cockroach> state){
        this.x=x;
        this.y=y;
        this.height=height;
        this.width=width;
        this.graphicComponent=graphicComponent;
        this.bodyComponent=bodyComponent;
        this.powerComponent=powerComponent;
        this.trajectoryComponent=trajectoryComponent;
        this.stateMachine=new DefaultStateMachine<Cockroach, State<Cockroach>>(this,state);
        tempDeltaTime=0;
        alpha=15;
    }

    void update(float deltaTime){
        tempDeltaTime=deltaTime;
        stateMachine.update();
    }

    Rectangle getBody(){
        return bodyComponent.getBody(x,y,width,height);
    }

    void draw(SpriteBatch sprite){
        graphicComponent.setCocroach(this);
        graphicComponent.draw(x,y,width,height,sprite);
    }

    void Pressed() {
        Telegram telegram=new Telegram();
        telegram.message=1;
        stateMachine.getCurrentState().onMessage(this,telegram);
    }

    void Released() {
        Telegram telegram=new Telegram();
        telegram.message=2;
        stateMachine.getCurrentState().onMessage(this,telegram);
    }

    public float getTempDeltaTime() {
        return tempDeltaTime;
    }

    public float getAlpha() {
        return alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public void dicrementAlpha(){alpha--;}

    public PowerComponent getPowerComponent() {
        return powerComponent;
    }

    public TrajectoryComponent getTrajectoryComponent() {
        return trajectoryComponent;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void dispose(){
        trajectoryComponent=null;
        powerComponent=null;
    }
}
