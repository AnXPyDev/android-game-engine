package engine.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class DrawRectCall implements DrawCall {
    RectF dst;
    Paint paint;

    public DrawRectCall(RectF dst, Paint paint) {
        this.dst = dst;
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(dst, paint);
    }
}
