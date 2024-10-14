package engine.event;

import engine.Vector;

public class TouchEvent implements GameEvent {
    public Vector pos;

    public TouchEvent(Vector pos) {
        this.pos = pos;
    }
}
