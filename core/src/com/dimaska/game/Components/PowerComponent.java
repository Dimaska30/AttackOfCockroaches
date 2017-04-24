package com.dimaska.game.Components;

import com.dimaska.game.Cockroach;

/**
 * Created by dimaska on 06.03.17.
 */

public interface PowerComponent {

    void superPower();

    void setCockroach(Cockroach cockroach);

    boolean MayClick();

    String getType();
}
