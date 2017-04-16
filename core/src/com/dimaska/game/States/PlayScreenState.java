package com.dimaska.game.States;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.dimaska.game.Screens.PlayScreen;

/**
 * Created by Администратор on 15.04.2017.
 */

public enum PlayScreenState implements State<PlayScreen> {
    Play() {
        @Override
        public void enter(PlayScreen entity) {

        }

        @Override
        public void update(PlayScreen entity) {

        }

        @Override
        public void exit(PlayScreen entity) {

        }
    },
    Pause() {
        @Override
        public void enter(PlayScreen entity) {

        }

        @Override
        public void update(PlayScreen entity) {

        }

        @Override
        public void exit(PlayScreen entity) {

        }
    },
    Lose() {
        @Override
        public void enter(PlayScreen entity) {

        }

        @Override
        public void update(PlayScreen entity) {

        }

        @Override
        public void exit(PlayScreen entity) {

        }
    },
    Win() {
        @Override
        public void enter(PlayScreen entity) {

        }

        @Override
        public void update(PlayScreen entity) {

        }

        @Override
        public void exit(PlayScreen entity) {

        }
    },
    FirstStartLvl() {
        @Override
        public void enter(PlayScreen entity) {

        }

        @Override
        public void update(PlayScreen entity) {

        }

        @Override
        public void exit(PlayScreen entity) {

        }
    },
    NewWave() {
        @Override
        public void enter(PlayScreen entity) {

        }

        @Override
        public void update(PlayScreen entity) {

        }

        @Override
        public void exit(PlayScreen entity) {

        }
    };

    @Override
    public void enter(PlayScreen entity) {

    }

    @Override
    public void update(PlayScreen entity) {

    }

    @Override
    public void exit(PlayScreen entity) {

    }

    @Override
    public boolean onMessage(PlayScreen entity, Telegram telegram) {
        return false;
    }
}
