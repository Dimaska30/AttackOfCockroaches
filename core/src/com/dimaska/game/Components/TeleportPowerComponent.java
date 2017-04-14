package com.dimaska.game.Components;

import com.dimaska.game.Cockroach;
import com.dimaska.game.GameConst;
import com.dimaska.game.States.NormallState;

/**
 * Created by Администратор on 04.04.2017.
 */

public class TeleportPowerComponent implements PowerComponent {

    private int points;
    private Cockroach cockroach;
    
    public TeleportPowerComponent(int points){
        this.points=points;
    }

    @Override
    public void superPower() {
        if(Math.random()<0.01){
        if(points!=0){
            if(cockroach!=null && cockroach.getY()>200 && cockroach.getY()< GameConst.Y-200){
                cockroach.setX((float) (Math.random() * (GameConst.X - GameConst.Cockroach_Width)));
                points--;
            }
        }
        }
    }

    @Override
    public void setCockroach(Cockroach cockroach) {
            this.cockroach = cockroach;
    }

    @Override
    public boolean MayClick() {
        if(cockroach.stateMachine.getCurrentState()== NormallState.Live){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public String getType() {
        return "Teleport";
    }
}
