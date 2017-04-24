package com.dimaska.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

/**
 * Created by Администратор on 31.03.2017.
 */

public class ScoreController implements Observer{
     private int score;
     private MyGame game;
     private int combo;

    ScoreController(MyGame game){
        score=0;
        combo=1;
        this.game=game;
    }

    public int getScore() {
        return score;
    }

    public int getCombo() {
        return combo;
    }

    private void restartCount(){
        SaveScore();
        score=0;
        combo=1;
    }

    private void SaveScore(){
        for(int i=0;i<10;i++){
            int records=game.user.getInteger("Score"+i,-1);
            if(score>records){
                game.user.putInteger("Score"+i,score);
                break;
            }
        }
        game.user.flush();
    }


    @Override
    public void onNotify(Cockroach cockroach, String event) {
        if (cockroach.getPowerComponent().getType().equals("Bum")) {
            if (event.equals("Release")) {
                if (cockroach.getY() > GameConst.Y / 2) {
                    score += 500 * combo;
                } else {
                    score += 250 * combo;
                }
                combo++;
            }
        } else if (cockroach.getPowerComponent().getType().equals("Teleport")) {
            if (event.equals("Press")) {
                if (cockroach.getY() > GameConst.Y / 2) {
                    score += 300 * combo;
                } else {
                    score += 200 * combo;
                }
                combo++;
            }
        } else if (cockroach.getPowerComponent().getType().equals("Null")) {
            if (event.equals("Press")) {
                if (cockroach.getY() > GameConst.Y / 2) {
                    score += 200 * combo;
                } else {
                    score += 100 * combo;
                }
                combo++;
            }
        }

    }

    @Override
    public void onNotify(float x, float y, String event) {
        if (event.equals("Miss")) {
            combo=1;
        }
        if (event.equals("Lose")) {
            restartCount();
        }
        if (event.equals("Win")) {
            restartCount();
        }
    }
}
