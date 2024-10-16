package engine.event;

public class TickEvent implements Event {
    public final double dt;
    Object notifyHandler;

    public TickEvent(double dt, Object notifyHandler) {
        this.dt = dt;
        this.notifyHandler = notifyHandler;
    }

    @Override
    public void onComplete() {
        synchronized (notifyHandler) {
            notifyHandler.notify();
        }
    }
}
