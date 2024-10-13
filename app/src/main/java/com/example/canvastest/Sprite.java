package com.example.canvastest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import androidx.annotation.DrawableRes;

public class Sprite {
    Bitmap bitmap;
    @DrawableRes int resource;

    public Sprite(@DrawableRes int resource) {
        this.resource = resource;
    }

    public void load(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), resource);
    }

    public Vector getSize() {
        return new Vector(bitmap.getWidth(), bitmap.getHeight());
    }

    public void draw(Canvas canvas, RectF dst) {
        draw(canvas, dst, null);
    }

    public void draw(Canvas canvas, RectF dst, Paint paint) {
        canvas.drawBitmap(bitmap, null, dst, paint);
    }
}
