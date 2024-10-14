package engine.graphics;

import android.graphics.Canvas;
import android.graphics.Paint;

public class DrawPaintCall implements DrawCall {
    Paint paint;
    public DrawPaintCall(Paint paint) {
        this.paint = paint;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPaint(paint);
    }
}
