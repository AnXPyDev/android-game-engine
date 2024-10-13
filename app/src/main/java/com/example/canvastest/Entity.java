package com.example.canvastest;

import android.graphics.Canvas;

public interface Entity {
    void spawn(Game game);
    void tick(Game game, double dt);
    void draw(Game game, Canvas canvas);
    boolean handleEvent(Game game, GameEvent event);
}
