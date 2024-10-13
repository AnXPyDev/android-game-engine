package com.example.canvastest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

public class Baloon implements Entity {
    static Sprite spr_baloon = new Sprite(R.drawable.spr_baloon);
    static Paint bg = new Paint();

    static Vector default_size = new Vector(200);

    public static void initStatic(Context context) {
        bg.setColor(Color.RED);
        spr_baloon.load(context);

        default_size.y *= (spr_baloon.getSize().x / default_size.x);
    }


    Vector pos = new Vector();
    Vector vel = new Vector();
    Vector size = new Vector(100);

    Paint color;

    @Override
    public void spawn(Game game) {

        color = new Paint();
        color.setColorFilter(new ColorMatrixColorFilter(new float[] {
            game.random.nextFloat(), 0, 0, 0, 0,
            0, game.random.nextFloat(), 0, 0, 0,
            0, 0, game.random.nextFloat(), 0, 0,
            0, 0, 0, 1, 0
        }));

        size = default_size.scale(new Vector(0.75 + game.random.nextDouble() * 0.5));

        pos.x = game.random.nextDouble() * (game.sceneSize.x - size.x) + size.x / 2.0;
        pos.y = game.sceneSize.y + game.random.nextDouble() * (game.sceneSize.y - size.y) + size.y / 2.0;
        vel.x = game.random.nextDouble() * 200.0 - 100.0;
        vel.y = (game.random.nextDouble() * 500.0 + 250.0) * (-1.0);

        if (game.random.nextBoolean()) {
            vel.x *= -1.0;
        }
    }

    @Override
    public void tick(Game game, double dt) {
        Vector npos = new Vector(pos.x + vel.x * dt, pos.y + vel.y * dt);

        Vector lcor = npos.lcor(size);
        Vector rcor = npos.rcor(size);

        if (lcor.x < 0 || rcor.x > game.sceneSize.x) {
            vel.x *= -1;
        }

        if (rcor.y < 0) {
            game.replaceBaloon(this);
        }

        pos.x += vel.x * dt;
        pos.y += vel.y * dt;
    }

    @Override
    public void draw(Game game, Canvas canvas) {
        spr_baloon.draw(canvas, pos.crect(size), color);
    }

    @Override
    public boolean handleEvent(Game game, GameEvent event) {
        if (event instanceof TouchEvent) {
            TouchEvent tevt = (TouchEvent)event;
            if (pos.pointInsideCrect(size, tevt.pos)) {
                game.replaceBaloon(this);
                return true;
            }
        }
        return false;
    }
}
