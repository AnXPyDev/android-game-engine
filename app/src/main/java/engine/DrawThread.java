package engine;

import android.util.Log;

import java.util.Queue;

import engine.graphics.DrawQueue;

public class DrawThread extends Thread {
    Game game;

    public DrawThread(Game game) {
        super();
        this.game = game;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        while (game.drawRunning) {
            DrawQueue queue;

            try {
                queue = game.readyFrames.takeLast();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            game.readyFrames.clear();

            long currentTime = System.nanoTime();
            double dt = (double)(currentTime - lastTime) / 1E9;

            game.draw(queue);

            lastTime = currentTime;
        }
    }
}
