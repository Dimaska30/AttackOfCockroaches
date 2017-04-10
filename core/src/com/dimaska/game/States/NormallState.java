package com.dimaska.game.States;

import com.badlogic.gdx.ai.fsm.*;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.dimaska.game.Cockroach;

/**
 * Created by Администратор on 04.04.2017.
 */

public enum NormallState implements State<Cockroach> {

    Live(){
        @Override
        public void update(Cockroach entity) {
            entity.setX(entity.getTrajectoryComponent().getX(entity.getTempDeltaTime(),entity.getX()));
            entity.setY(entity.getTrajectoryComponent().getY(entity.getTempDeltaTime(),entity.getY()));
            //entity.getPowerComponent().setCockroach(entity);
            entity.getPowerComponent().superPower();
        }
    },

    Crashed(){

        @Override
        public void update(Cockroach entity){
            entity.dicrementAlpha();
            if(entity.getAlpha()<0){
                entity.stateMachine.changeState(Dead);
            }
        }
    },

    Dead(){
        @Override
        public void update(Cockroach entity) {

        }
    }

    ;
    @Override
    public void enter(Cockroach entity) {

    }



    @Override
    public void exit(Cockroach entity) {

    }

    @Override
    public boolean onMessage(Cockroach entity, Telegram telegram) {
        if(telegram.message==1){
            entity.stateMachine.changeState(NormallState.Crashed);
        }
        return false;
    }
}
