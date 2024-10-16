package engine.event;

import java.util.HashMap;
import java.util.List;

public class EventService implements EventHandler {
    private final List<EventWorkerPool> pools;
    private final HashMap<Class<? extends Event>, EventWorkerPool> poolDirectory;

    public EventService(List<EventWorkerPool> pools, HashMap<Class<? extends Event>, EventWorkerPool> poolDirectory) {
        this.pools = pools;
        this.poolDirectory = poolDirectory;
    }

    public EventHandler getEventHandler(Class<? extends Event> eventClass) {
        EventHandler pool = poolDirectory.get(eventClass);
        if (pool == null) {
            throw new RuntimeException(String.format("event handler for %s not found", eventClass));
        }
        return pool;
    }

    public void start() {
        for (EventWorkerPool pool : pools) {
            pool.start();
        }
    }

    public void stop() throws InterruptedException {
        for (EventWorkerPool pool : pools) {
            pool.stop();
        }
    }

    @Override
    public void submitEvent(Event event) {
        getEventHandler(event.getClass()).submitEvent(event);
    }

    @Override
    public EventListener addListener(EventListener listener) {
        return getEventHandler(listener.getEventClass()).addListener(listener);
    }

    @Override
    public void removeListener(EventListener listener) {
        getEventHandler(listener.getEventClass()).removeListener(listener);

    }

    @Override
    public EventListener addListener(Class<? extends Event> eventClass, EventListener listener) {
        return getEventHandler(eventClass).addListener(eventClass, listener);
    }

    @Override
    public void removeListener(Class<? extends Event> eventClass, EventListener listener) {
        getEventHandler(eventClass).removeListener(eventClass, listener);
    }
}
