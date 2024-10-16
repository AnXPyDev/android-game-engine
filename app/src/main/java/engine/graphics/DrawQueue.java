package engine.graphics;

import java.util.Collection;

public interface DrawQueue {
    void push(DrawCall call);
    void merge(Collection<DrawCall> calls);
    Collection<DrawCall> getCalls();

    default void push(DrawCall call, int index) {
        push(new IndexedDrawCall(call, index));
    }
}
