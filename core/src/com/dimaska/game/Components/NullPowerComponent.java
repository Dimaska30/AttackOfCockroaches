package com.dimaska.game.Components;

import com.dimaska.game.Cockroach;
import com.dimaska.game.States.NormallState;

/**
 * Created by Администратор on 04.04.2017.
 */

public class NullPowerComponent implements PowerComponent {
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
        return cockroach.stateMachine.getCurrentState() == NormallState.Live;
    }

    @Override
    public String getType() {
        return "Null";
    }
}
