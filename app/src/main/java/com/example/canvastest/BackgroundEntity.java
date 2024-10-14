package com.example.canvastest;

import android.graphics.Color;
import android.graphics.Paint;

import engine.Game;
import engine.entity.Entity;
import engine.graphics.DrawPaintCall;
import engine.graphics.DrawQueue;

public class BackgroundEntity implements Entity {
    Paint bg = new Paint();

    public BackgroundEntity() {
        bg.setColor(Color.WHITE);
    }

    @Override
    public void draw(Game game, DrawQueue queue) {
        queue.push(new DrawPaintCall(bg), DrawQueue.LAYER_BG);
    }
}
