package engine.graphics;

import android.graphics.Canvas;

import java.util.Comparator;

public class LateSortedDrawQueue extends UnsortedDrawQueue {
    @Override
    public void draw(Canvas canvas) {
        calls.sort(Comparator.comparingInt(DrawCall::getIndex));
        super.draw(canvas);
    }
}
