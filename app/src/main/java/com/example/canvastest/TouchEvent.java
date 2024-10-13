package com.example.canvastest;

import android.text.method.Touch;

public class TouchEvent implements GameEvent {
    Vector pos;

    public TouchEvent(Vector pos) {
        this.pos = pos;
    }
}
