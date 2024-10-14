package engine.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class DrawBitmapCall implements DrawCall {
    Bitmap bitmap;
    Rect src;
    RectF dst;
    Paint paint;

    public DrawBitmapCall(Bitmap bitmap, Rect src, RectF dst, Paint paint) {
        this.bitmap = bitmap;
        this.src = src;
        this.dst = dst;
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bitmap, src, dst, paint);
    }
}
