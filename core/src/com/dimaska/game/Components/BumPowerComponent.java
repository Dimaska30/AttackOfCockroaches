package com.dimaska.game.Components;

import com.dimaska.game.Cockroach;
import com.dimaska.game.States.BumState;
import com.dimaska.game.World;

/**
 * Created by Администратор on 05.04.2017.
 */

public class BumPowerComponent implements PowerComponent {

    private Cockroach cockroach;

    @Override
    public void superPower() {

    }

    @Override
    public void setCockroach(Cockroach cockroach) {
        this.cockroach=cockroach;
    }

    @Override
    public boolean MayClick() {
        return cockroach.stateMachine.getCurrentState() == BumState.Live;
    }

    @Override
    public String getType() {
        return "Bum";
    }
}
