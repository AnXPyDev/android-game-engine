package engine;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

import engine.entity.EntityManager;
import engine.event.GameEvent;
import engine.event.TouchListener;
import engine.graphics.DrawCall;
import engine.graphics.DrawQueue;

public class Game {
    public boolean gameRunning = false;
    public boolean drawRunning = false;

    public Scene activeScene;
    private GameThread gameThread = new GameThread(this);
    private DrawThread drawThread = new DrawThread(this);

    public EntityManager entityManager;

    private SurfaceView displayView;
    private SurfaceHolder displaySurfaceHolder;

    public Vector displaySize;

    ConcurrentLinkedQueue<GameEvent> eventQueue = new ConcurrentLinkedQueue<>();
    BlockingDeque<DrawQueue> readyFrames = new LinkedBlockingDeque<>();

    public final Random random = new Random();

    public View initDisplay(Context context) {
        displayView = new SurfaceView(context);
        displayView.setOnTouchListener(new TouchListener(this));
        return displayView;
    }

    public void start() {
        displaySurfaceHolder = displayView.getHolder();
        displaySize = new Vector(displayView.getWidth(), displayView.getHeight());
        drawRunning = true;
        drawThread.start();
    }

    public void tick(double dt) {
        System.gc();
        entityManager.tick(this, dt);

        while (true) {
            GameEvent event = eventQueue.poll();
            if (event == null) {
                break;
            }
            entityManager.handleEvent(this, event);
        }

        if (readyFrames.isEmpty()) {
            DrawQueue drawQueue = new DrawQueue();
            entityManager.draw(this, drawQueue);
            readyFrames.add(drawQueue);
        }
    }

    public void draw(DrawCall call) {
        Canvas canvas = displaySurfaceHolder.lockHardwareCanvas();
        if (canvas == null) {
            return;
        }
        call.draw(canvas);
        displaySurfaceHolder.unlockCanvasAndPost(canvas);
    }

    public void postEvent(GameEvent event) {
        eventQueue.add(event);
    }

    public void replaceScene(Scene scene) {
        gameRunning = false;

        try {
            gameThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (activeScene != null) {
            activeScene.destroy();
        }

        activeScene = scene;

        entityManager = new EntityManager();

        activeScene.init(this);

        gameRunning = true;
        gameThread.start();
    }

}
