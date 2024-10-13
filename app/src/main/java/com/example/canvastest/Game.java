package com.example.canvastest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Game {
    ReentrantLock queueMutex = new ReentrantLock();
    ReentrantLock tickMutex = new ReentrantLock();
    public ArrayList<GameEvent> eventQueue = new ArrayList<>();

    public Random random = new Random();

    static Paint bg = new Paint();
    public static void initStatic(Context context) {
        bg.setColor(Color.WHITE);
        Baloon.initStatic(context);
    }


    public GameManager gameManager;
    public EntityManager entityManager = new EntityManager();

    public Game(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    Vector sceneSize;

    public void init() {
        sceneSize = gameManager.getSceneSize();

        for (int i = 0; i < 10; i++) {
            entityManager.spawn(new Baloon());
        }
    }

    public void draw(Canvas canvas) {
        tickMutex.lock();
        canvas.drawPaint(bg);
        for (Entity entity : entityManager.entities) {
            entity.draw(this, canvas);
        }
        tickMutex.unlock();
    }

    public void tick(double dt) {
        tickMutex.lock();
        entityManager.tick(this);
        for (Entity entity : entityManager.entities) {
            entity.tick(this, dt);
        }

        gameManager.redraw();

        queueMutex.lock();
        ArrayList<GameEvent> eq = eventQueue;
        eventQueue = new ArrayList<>();
        queueMutex.unlock();

        for (GameEvent event : eq) {
            for (Entity entity : entityManager.entities) {
                if (entity.handleEvent(this, event)) {
                    break;
                }
            }
        }

        eq.clear();

        tickMutex.unlock();

    }

    public void postEvent(GameEvent event) {
        queueMutex.lock();
        eventQueue.add(event);
        queueMutex.unlock();
    }

    public void replaceBaloon(Baloon baloon) {
        entityManager.kill(baloon);
        entityManager.spawn(new Baloon());
    }
}
