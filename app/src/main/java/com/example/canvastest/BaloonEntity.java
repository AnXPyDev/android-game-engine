package com.example.canvastest;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.Log;

import engine.Box;
import engine.Game;
import engine.Vector;
import engine.assets.BulkLoader;
import engine.entity.Entity;
import engine.event.GameEvent;
import engine.event.TouchEvent;
import engine.graphics.DrawBitmapCall;
import engine.graphics.DrawQueue;
import engine.graphics.DrawRectCall;
import engine.graphics.BitmapDrawable;

public class BaloonEntity implements Entity {

    private static BitmapDrawable spr_baloon = BitmapDrawable.fromResource(R.drawable.spr_baloon);

    public static void initStatic(BulkLoader bulkLoader) {
        bulkLoader.register(spr_baloon);
    }

    long tickCount = 0;

    Vector pos = new Vector();
    Vector vel = new Vector(100, -500);
    Vector size = new Vector(200, 200);
    Paint color = new Paint();

    @Override
    public void init(Game game) {
        size.scale(1.0, 1.0 / spr_baloon.getAspect());
        size.scale(game.random.nextDouble() * 0.5 + 0.5);
        pos.replace(Box.randomPos(game.displaySize.product(0.5).sum(0, game.displaySize.y), game.displaySize.sum(size.product(-1)), game.random));
        vel.scale(game.random.nextDouble() * 2.0 - 1.0, game.random.nextDouble());
        color.setColorFilter(new ColorMatrixColorFilter(new float[] {
            game.random.nextFloat(), 0, 0, 0, 0,
            0, game.random.nextFloat(), 0, 0, 0,
            0, 0, game.random.nextFloat(), 0, 0,
            0, 0, 0, 1, 0
        }));
    }

    @Override
    public void draw(Game game, DrawQueue queue) {
        queue.push(new DrawBitmapCall(spr_baloon.getBitmap(), null, Box.toRectF(pos, size), color), DrawQueue.LAYER_BG);
    }

    @Override
    public void tick(Game game, double dt) {
        Vector newPos = pos.sum(vel.product(dt));
        Vector lcor = Box.lcor(newPos, size);
        Vector rcor = Box.rcor(newPos, size);

        if (lcor.x < 0 || rcor.x > game.displaySize.x) {
            vel.x *= -1.0;
        }

        if (rcor.y < 0) {
            BaloonScene.replaceBaloon(game, this);
        }

        pos.add(vel.product(dt));
    }

    @Override
    public boolean handle(Game game, GameEvent event) {
        if (event instanceof TouchEvent) {
            if (Box.collides(pos, size, ((TouchEvent)event).pos)) {
                BaloonScene.replaceBaloon(game, this);
                return true;
            }
        }
        return false;
    }
}
