package com.dimaska.game;

/**
 * Created by Администратор on 03.04.2017.
 */

public class AchievementSystem implements Observer {

    private int killAllCockroaches;
    private int killTeleportCockroaches;
    private int killBumCockroaches;
    private int clickes;
    private int numMiss;
    private int numWin;
    private int numLose;
    private int numWave;
    private float playTime;
    private int tryKillBumCockroaches;
    private boolean updated;

    AchievementSystem(
            int killAllCockroaches,
            int killTeleportCockroaches,
            int killBumCockroaches,
            int clickes,
            int numMiss,
            int numWin,
            int numLose,
            int numWave,
            int playTime,
            int tryKillBumCockroaches) {
        this.killAllCockroaches = killAllCockroaches;
        this.killTeleportCockroaches = killTeleportCockroaches;
        this.killBumCockroaches = killBumCockroaches;
        this.clickes = clickes;
        this.numMiss = numMiss;
        this.numWin = numWin;
        this.numLose = numLose;
        this.numWave = numWave;
        this.playTime = playTime;
        this.tryKillBumCockroaches = tryKillBumCockroaches;
        checkAchievements();
        updated = false;
    }

    public void update(float deltaTime) {
        playTime += deltaTime;
        if (updated) {
            checkAchievements();
        }
    }

    ;

    @Override
    public void onNotify(Cockroach cockroach, String event) {
        if (event.equals("Press")) {
            killAllCockroaches++;
            clickes++;
            if (!cockroach.getPowerComponent().MayClick()) {
                if (cockroach.getPowerComponent().getType().equals("Teleport")) {
                    killTeleportCockroaches++;
                } else if (cockroach.getPowerComponent().getType().equals("Bum")) {
                    killBumCockroaches++;
                }
            }
        }
        if (event.equals("Release")) {

        }
        updated = true;
    }

    @Override
    public void onNotify(float x, float y, String event) {
        if (event.equals("Lose")) {
            numLose++;
        } else if (event.equals("Miss")) {
            clickes++;
            numMiss++;
        } else if (event.equals("Win")) {
            numWin++;
        } else if (event.equals("NewWave")) {
            numWave++;
        }
        updated = true;
    }

    private void checkAchievements() {
        updated = false;
    }
}
