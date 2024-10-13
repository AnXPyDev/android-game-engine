package com.example.canvastest;

public class GameThread extends Thread {
    Game game;

    public GameThread(Game game) {
        super();
        this.game = game;
    }

    @Override
    public void run() {
        while(true) {
            double dt = 1.0 / 60.0;
            game.tick(dt);
            try {
                sleep((int)(dt * 1000.0));
            } catch (InterruptedException e) {
                break;
            }
        }
    }

}
