package com.example.canvastest;

import android.graphics.Canvas;

public interface GameManager {
    void redraw();
    void draw(Canvas canvas);
    Vector getSceneSize();
    void postEvent(GameEvent event);
}
