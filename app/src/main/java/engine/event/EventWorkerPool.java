package engine.event;

import android.util.Log;

import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventWorkerPool implements EventWorkerContext, EventHandler {
    private final ConcurrentLinkedQueue<Event> pendingEvents = new ConcurrentLinkedQueue<>();
    private final ConcurrentLinkedQueue<Runnable> pendingTasks = new ConcurrentLinkedQueue<>();
    private final Object notifyHandler = new Object();

    private final HashMap<Class<? extends Event>, SortedEventListenerList> listenerMap = new HashMap<>();

    private EventWorkerThread[] workers;

    private void log(String format, Object... objects) {
        Log.d("POOL", String.format("(T%s) ", Thread.currentThread().getId()) + String.format(format, objects));
    }

    public EventWorkerPool(int workerCount, Iterable<Class<? extends Event>> eventClasses) {
        workers = new EventWorkerThread[workerCount];

        for (int i = 0; i < workerCount; i++) {
            workers[i] = new EventWorkerThread(this);
        }

        for (Class<? extends Event> eventClass : eventClasses) {
            listenerMap.put(eventClass, new SortedEventListenerList());
        }
    }

    public void submitTask(Runnable task) {
        //log("Submit task %s", task);
        pendingTasks.add(task);
        synchronized (notifyHandler) {
            notifyHandler.notify();
        }
    }

    public void submitEvent(Event event) {
        //log("Submit event %s", event);
        pendingEvents.add(event);
        synchronized (notifyHandler) {
            notifyHandler.notify();
        }
    }

    @Override
    public EventListener addListener(EventListener listener) {
        addListener(listener.getEventClass(), listener);
        return listener;
    }

    @Override
    public void removeListener(EventListener listener) {
        removeListener(listener.getEventClass(), listener);
    }

    @Override
    public EventListener addListener(Class<? extends Event> eventClass, EventListener listener) {
        //log("Add listener %s", listener);
        final SortedEventListenerList listeners = listenerMap.get(eventClass);
        submitTask(() -> {
            listeners.lock.writeLock().lock();
            listeners.add(listener);
            listeners.lock.writeLock().unlock();
        });
        return listener;
    }

    @Override
    public void removeListener(Class<? extends Event> eventClass, EventListener listener) {
        //log("Remove listener %s", listener);
        final SortedEventListenerList listeners = listenerMap.get(eventClass);
        submitTask(() -> {
            listeners.lock.writeLock().lock();
            listeners.remove(listener);
            listeners.lock.writeLock().unlock();
        });
    }

    @Override
    public void handleEvent(Event event) {
        //log("Handle event %s", event);
        SortedEventListenerList listeners = listenerMap.get(event.getClass());
        listeners.lock.readLock().lock();
        for (EventListener listener : listeners) {
            synchronized (listener) {
                listener.handle(event);
            }
        }
        listeners.lock.readLock().unlock();
        event.onComplete();
    }

    @Override
    public void handleTask(Runnable task) {
        //log("Handle task %s", task);
        task.run();
    }

    @Override
    public Event pollEvent() {
        //log("Poll event");
        return pendingEvents.poll();
    }

    @Override
    public Runnable pollTask() {
        //log("Poll task");
        return pendingTasks.poll();
    }

    @Override
    public Object getNotifyHandler() {
        return notifyHandler;
    }

    public void start() {
        for (EventWorkerThread worker : workers) {
            worker.start();
        }
    }

    public void stop() throws InterruptedException {
        for (EventWorkerThread worker : workers) {
            worker.interrupt();
            worker.join();
        }
    }

}
