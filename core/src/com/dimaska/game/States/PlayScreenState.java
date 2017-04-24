package com.dimaska.game.States;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.dimaska.game.GameConst;
import com.dimaska.game.Screens.PlayScreen;

/**
 * Created by Администратор on 15.04.2017.
 */

public enum PlayScreenState implements State<PlayScreen> {
    Play() {
        @Override
        public void enter(PlayScreen entity) {
            entity.getLabelPause().setVisible(false);
            entity.getLoseDialog().setVisible(false);
            entity.getLabelScore().setVisible(true);
            entity.getLabelCombo().setVisible(true);
            entity.getLabelLvl().setVisible(false);
            entity.getLabelWave().setVisible(false);
            entity.getWinDialog().setVisible(false);
        }

        @Override
        public void update(PlayScreen entity) {
            entity.getWorld().update(true, entity.getTempDelta());
            entity.getLabelScore().setText("Score:" + entity.getWorld().scope.getScore());
            entity.getLabelCombo().setText("x" + entity.getWorld().scope.getCombo());
            entity.getBatch().begin();
            entity.getShape().begin(ShapeRenderer.ShapeType.Filled);
            entity.getShape().setColor(0, 0, 0, 255 / 255.f);
            entity.getShape().rect(0, entity.getLabelCombo().getY(), GameConst.X, GameConst.Y - entity.getLabelCombo().getY());
            entity.getShape().end();
            entity.getUI().draw();
            entity.getBatch().end();
        }
    },
    Pause() {
        @Override
        public void enter(PlayScreen entity) {
            entity.getLabelPause().setVisible(true);
            entity.getLoseDialog().setVisible(false);
            entity.getLabelScore().setVisible(true);
            entity.getLabelCombo().setVisible(true);
            entity.getLabelLvl().setVisible(false);
            entity.getLabelWave().setVisible(false);
            entity.getWinDialog().setVisible(false);
        }

        @Override
        public void update(PlayScreen entity) {
            entity.getWorld().update(true, entity.getTempDelta());
            entity.getBatch().begin();
            entity.getShape().begin(ShapeRenderer.ShapeType.Filled);
            entity.getShape().setColor(0, 0, 0, 255 / 255.f);
            entity.getShape().rect(0, 0, GameConst.X, GameConst.Y);
            entity.getShape().end();
            entity.getUI().draw();
            entity.getBatch().end();
        }
    },
    Lose() {
        @Override
        public void enter(PlayScreen entity) {
            entity.getLabelPause().setVisible(false);
            entity.getLoseDialog().setVisible(true);
            entity.getLabelScore().setVisible(true);
            entity.getLabelCombo().setVisible(true);
            entity.getLabelLvl().setVisible(false);
            entity.getLabelWave().setVisible(false);
            entity.getWinDialog().setVisible(false);
        }

        @Override
        public void update(PlayScreen entity) {
            entity.getWorld().update(false, entity.getTempDelta());
            entity.getBatch().begin();
            entity.getShape().begin(ShapeRenderer.ShapeType.Filled);
            entity.getShape().setColor(0, 0, 0, 255 / 255.f);
            entity.getShape().rect(0, entity.getLabelCombo().getY(), GameConst.X, GameConst.Y - entity.getLabelCombo().getY());
            entity.getShape().end();
            entity.getUI().draw();
            entity.getBatch().end();
        }
    },
    Win() {
        @Override
        public void enter(PlayScreen entity) {
            entity.getLabelPause().setVisible(false);
            entity.getLoseDialog().setVisible(false);
            entity.getLabelScore().setVisible(true);
            entity.getLabelCombo().setVisible(true);
            entity.getLabelLvl().setVisible(false);
            entity.getLabelWave().setVisible(false);
            entity.getWinDialog().setVisible(true);
        }

        @Override
        public void update(PlayScreen entity) {
            entity.getWorld().update(false, entity.getTempDelta());
            entity.getBatch().begin();
            entity.getShape().begin(ShapeRenderer.ShapeType.Filled);
            entity.getShape().setColor(0, 0, 0, 255 / 255.f);
            entity.getShape().rect(0, entity.getLabelCombo().getY(), GameConst.X, GameConst.Y - entity.getLabelCombo().getY());
            entity.getShape().end();
            entity.getUI().draw();
            entity.getBatch().end();
        }
    },
    FirstStartLvl() {
        float currentTime = 0;

        @Override
        public void enter(PlayScreen entity) {
            entity.getLabelPause().setVisible(false);
            entity.getLoseDialog().setVisible(false);
            entity.getLabelScore().setVisible(true);
            entity.getLabelCombo().setVisible(true);
            entity.getLabelLvl().setVisible(true);
            entity.getLabelWave().setVisible(false);
            entity.getWinDialog().setVisible(false);
            currentTime = 0;
        }

        @Override
        public void update(PlayScreen entity) {
            currentTime += entity.getTempDelta();
            entity.getWorld().update(false, entity.getTempDelta());
            entity.getBatch().begin();
            entity.getShape().begin(ShapeRenderer.ShapeType.Filled);
            entity.getShape().setColor(0, 0, 0, 255 / 255.f);
            entity.getShape().rect(0, entity.getLabelCombo().getY(), GameConst.X, GameConst.Y - entity.getLabelCombo().getY());
            entity.getShape().end();
            entity.getUI().draw();
            entity.getBatch().end();
            if (currentTime >= GameConst.MaxTimeForNotification) {
                entity.getStateMachine().changeState(NewWave);
            }
        }
    },
    NewWave() {
        float currentTime = 0;

        @Override
        public void enter(PlayScreen entity) {
            entity.getLabelPause().setVisible(false);
            entity.getLoseDialog().setVisible(false);
            entity.getLabelScore().setVisible(true);
            entity.getLabelCombo().setVisible(true);
            entity.getLabelLvl().setVisible(false);
            entity.getLabelWave().setVisible(true);
            entity.getWinDialog().setVisible(false);
            currentTime = 0;
        }

        @Override
        public void update(PlayScreen entity) {
            currentTime += entity.getTempDelta();
            entity.getWorld().update(false, entity.getTempDelta());
            entity.getBatch().begin();
            entity.getShape().begin(ShapeRenderer.ShapeType.Filled);
            entity.getShape().setColor(0, 0, 0, 255 / 255.f);
            entity.getShape().rect(0, entity.getLabelCombo().getY(), GameConst.X, GameConst.Y - entity.getLabelCombo().getY());
            entity.getShape().end();
            entity.getUI().draw();
            entity.getBatch().end();
            if (currentTime >= GameConst.MaxTimeForNotification) {
                entity.getStateMachine().changeState(Play);
            }
        }

    },

    Close() {
        @Override
        public void enter(PlayScreen entity) {
            entity.getLabelPause().setVisible(false);
            entity.getLoseDialog().setVisible(true);
            entity.getLabelScore().setVisible(true);
            entity.getLabelCombo().setVisible(true);
            entity.getLabelLvl().setVisible(false);
            entity.getLabelWave().setVisible(false);
            entity.getWinDialog().setVisible(false);
        }

        @Override
        public void update(PlayScreen entity) {
            entity.getWorld().update(false, entity.getTempDelta());
            entity.getBatch().begin();
            entity.getShape().begin(ShapeRenderer.ShapeType.Filled);
            entity.getShape().setColor(0, 0, 0, 255 / 255.f);
            entity.getShape().rect(0, entity.getLabelCombo().getY(), GameConst.X, GameConst.Y - entity.getLabelCombo().getY());
            entity.getShape().end();
            entity.getUI().draw();
            entity.getBatch().end();
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
