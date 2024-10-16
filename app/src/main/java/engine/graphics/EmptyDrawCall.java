package engine.graphics;

import android.graphics.Canvas;

public final class EmptyDrawCall implements DrawCall {
    @Override
    public void draw(Canvas canvas) {}

    public static final EmptyDrawCall INSTANCE = new EmptyDrawCall();
}
