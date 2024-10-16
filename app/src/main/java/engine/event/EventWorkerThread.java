package engine.event;

import android.util.Log;

import java.util.concurrent.ConcurrentLinkedQueue;

public class EventWorkerThread extends Thread {
    private final EventWorkerContext context;
    private final Object notifyHandler;

    public EventWorkerThread(EventWorkerContext context) {
        this.context = context;
        notifyHandler = context.getNotifyHandler();
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                Runnable task = context.pollTask();
                if (task != null) {
                    context.handleTask(task);
                    continue;
                }
                Event event = context.pollEvent();
                if (event != null) {
                    context.handleEvent(event);
                    continue;
                }

                synchronized (notifyHandler) {
                    //Log.i("POOL", String.format("(T%s) WAIT", getId()));
                    notifyHandler.wait();
                    //Log.i("POOL", String.format("(T%s) WAKE", getId()));
                }
            }
        } catch (InterruptedException ignored) {}
    }
}
