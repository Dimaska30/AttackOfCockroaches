package com.dimaska.game.States;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.dimaska.game.Cockroach;

/**
 * Created by Администратор on 05.04.2017.
 */

public enum BumState implements State<Cockroach> {

    Live(){
        @Override
        public void update(Cockroach entity) {
            entity.setX(entity.getTrajectoryComponent().getX(entity.getTempDeltaTime(),entity.getX()));
            entity.setY(entity.getTrajectoryComponent().getY(entity.getTempDeltaTime(),entity.getY()));
        }
    },

    Crashed(){

        @Override
        public void update(Cockroach entity){
            entity.setAlpha(entity.getAlpha()-entity.getTempDeltaTime());
            entity.getPowerComponent().superPower();
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
            entity.stateMachine.changeState(BumState.Crashed);
        }
        return false;
    }
}
