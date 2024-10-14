package engine;

public class GameThread extends Thread {
    Game game;

    public GameThread(Game game) {
        super();
        this.game = game;
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        while (game.gameRunning) {
            long currentTime = System.nanoTime();
            double dt = (double)(currentTime - lastTime) / 1E9;
            game.tick(dt);
            lastTime = currentTime;

            try {
                sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
