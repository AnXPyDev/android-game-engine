package engine.graphics;

import android.graphics.Canvas;

public interface DrawCall {
    void draw(Canvas canvas);

    default int getIndex() {
        throw new UnsupportedOperationException("tried to getIndex on non indexed draw call");
    }
}
