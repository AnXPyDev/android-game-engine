package engine.graphics;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class UnsortedDrawQueue implements DrawCall, DrawQueue {
    ArrayList<DrawCall> calls;

    public UnsortedDrawQueue() {
        calls = new ArrayList<>();
    }

    public UnsortedDrawQueue(int initialCapacity) {
        calls = new ArrayList<>(initialCapacity);
    }

    @Override
    public void push(DrawCall call) {
        calls.add(call);
    }

    @Override
    public void merge(Collection<DrawCall> newCalls) {
        calls.addAll(newCalls);
    }

    @Override
    public Collection<DrawCall> getCalls() {
        return calls;
    }

    public void draw(Canvas canvas) {
        for (DrawCall call : calls) {
            call.draw(canvas);
        }
    }

}
