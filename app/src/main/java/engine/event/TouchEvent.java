package engine.event;

import engine.util.Vector;

public class TouchEvent implements Event {
    private Vector pos;

    public TouchEvent(Vector pos) {
        this.pos = pos;
    }
    public Vector getPos() { return this.pos; }
}
