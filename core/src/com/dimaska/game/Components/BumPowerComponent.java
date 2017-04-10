package com.dimaska.game.Components;

import com.dimaska.game.Cockroach;
import com.dimaska.game.States.BumState;
import com.dimaska.game.World;

/**
 * Created by Администратор on 05.04.2017.
 */

public class BumPowerComponent implements PowerComponent {
    //private World world;
    private Cockroach cockroach;

    /*public BumPowerComponent(World world){
        this.world=world;
    }*/

    @Override
    public void superPower() {
        if(cockroach.getAlpha()>0){
            //world.setSlime(true);

        }else{
            //world.setSlime(false);
        }
    }

    @Override
    public void setCockroach(Cockroach cockroach) {
        this.cockroach=cockroach;
    }

    @Override
    public boolean isLive() {
        if(cockroach.stateMachine.getCurrentState()== BumState.Live){
            return true;
        }else {
            return false;
        }
    }
}
