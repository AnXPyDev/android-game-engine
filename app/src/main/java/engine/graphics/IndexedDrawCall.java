package engine.graphics;

import android.graphics.Canvas;

public final class IndexedDrawCall implements DrawCall {
    private DrawCall drawCall;
    private int index;

    public IndexedDrawCall(DrawCall drawCall, int index) {
        this.index = index;
        this.drawCall = drawCall;
    }

    @Override
    public void draw(Canvas canvas) {
        drawCall.draw(canvas);
    }

    @Override
    public int getIndex() {
        return index;
    }
}
