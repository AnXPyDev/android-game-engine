package engine.core;

import engine.event.Event;
import engine.event.EventHandler;
import engine.event.TickEvent;

public class LogicThread extends Thread {
    private EngineContext engine;
    private EventHandler tickHandler;
    private final Object notifyHandler = new Object();

    public LogicThread(EngineContext engine) {
        super();
        this.engine = engine;
    }

    @Override
    public void run() {
        tickHandler = engine.getEventHandler(TickEvent.class);

        long sleepDuration = 4;
        long lastTime = System.nanoTime();

        try {
            while (!isInterrupted()) {
                long preTickTime = System.nanoTime();

                double dt = (double)(preTickTime - lastTime) / 1E9;
                tickHandler.submitEvent(new TickEvent(dt, notifyHandler));

                synchronized (notifyHandler) {
                    notifyHandler.wait();
                }

                long postTickTime = System.nanoTime();

                engine.getPerformanceMonitor().tickDelta.set(PerformanceMonitor.nanosToMicros(preTickTime - lastTime));
                engine.getPerformanceMonitor().tickTime.set(PerformanceMonitor.nanosToMicros(postTickTime - preTickTime));

                lastTime = preTickTime;

                if (sleepDuration > 0) {
                    sleep(sleepDuration);
                }
                System.gc();
            }
        } catch (InterruptedException ignored) {}
    }
}
