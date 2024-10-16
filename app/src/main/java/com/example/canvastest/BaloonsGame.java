package com.example.canvastest;

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import engine.core.EngineContext;
import engine.core.EngineGame;
import engine.event.EventHandler;
import engine.event.EventListener;
import engine.event.SimpleEventListener;
import engine.event.TickEvent;
import engine.graphics.DrawPaintCall;
import engine.graphics.DrawQueue;

public class BaloonsGame implements EngineGame {
    private EngineContext engine;
    private EventHandler tickHanlder;

    private EventListener preTickEventListener;
    private EventListener postTickEventListener;

    private Paint bg = new Paint();

    @Override
    public void init(EngineContext engineContext) {
        engine = engineContext;

        bg.setColor(Color.BLUE);

        tickHanlder = engine.getEventHandler(TickEvent.class);

        postTickEventListener = tickHanlder.addListener(
            SimpleEventListener.create(TickEvent.class, Integer.MIN_VALUE + 1, this::postTick)
        );

        preTickEventListener = tickHanlder.addListener(
            SimpleEventListener.create(TickEvent.class, Integer.MIN_VALUE, this::preTick)
        );
    }

    public void preTick(TickEvent event) {
        engine.submitFrame(new DrawPaintCall(bg));
    }

    public void postTick(TickEvent event) {
    }
}
