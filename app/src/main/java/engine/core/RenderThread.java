package engine.core;

import java.util.concurrent.atomic.AtomicReference;

import engine.graphics.DrawCall;

public class RenderThread extends Thread {

    private final EngineContext engine;
    private DisplayManager displayManager;
    private boolean running;

    public RenderThread(EngineContext engine) {
        super();
        this.engine = engine;
    }

    @Override
    public synchronized void start() {
        running = true;
        displayManager = this.engine.getDisplayManager();
        super.start();
    }

    private final AtomicReference<DrawCall> readyFrame = new AtomicReference<>(null);
    private final Object notifyHandler = new Object();

    public void submitFrame(DrawCall frame) {
        DrawCall previousFrame = readyFrame.getAndSet(frame);

        synchronized (notifyHandler) {
            notifyHandler.notify();
        }

        if (previousFrame != null) {
            engine.getPerformanceMonitor().droppedFrames.incrementAndGet();
        }
    }

    @Override
    public void run() {
        try {
            long lastTime = System.nanoTime();
            long idleTime = 0;
            while (!isInterrupted()) {
                DrawCall frame = readyFrame.getAndSet(null);
                if (frame == null) {
                    idleTime = System.nanoTime();
                    synchronized (notifyHandler) {
                        notifyHandler.wait();
                    }
                    idleTime = System.nanoTime() - idleTime;
                    continue;
                }


                long renderBeginTime = System.nanoTime();
                displayManager.render(frame);
                long renderEndTime = System.nanoTime();

                engine.getPerformanceMonitor().drawTime.set(PerformanceMonitor.nanosToMicros(renderEndTime - renderBeginTime));
                engine.getPerformanceMonitor().drawDelta.set(PerformanceMonitor.nanosToMicros(renderBeginTime - lastTime));
                engine.getPerformanceMonitor().drawIdle.set(PerformanceMonitor.nanosToMicros(idleTime));

                idleTime = 0;
                lastTime = renderBeginTime;
            }
        } catch (InterruptedException ignored) {}
    }
}
