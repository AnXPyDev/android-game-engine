package engine.graphics;

import android.graphics.Canvas;

public final class CompositeDrawCall implements DrawCall {
    DrawCall[] calls;

    public CompositeDrawCall(DrawCall[] calls) {
        this.calls = calls;
    }

    @Override
    public void draw(Canvas canvas) {
        for (DrawCall call : calls) {
            call.draw(canvas);
        }
    }
}
