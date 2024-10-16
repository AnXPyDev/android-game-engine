package engine.event;

import java.util.ArrayList;
import java.util.Collection;

public class EventWorkerPoolBuilder {
    private int workerCount;
    private ArrayList<Class<? extends Event>> handledEvents = new ArrayList<>();

    public EventWorkerPoolBuilder(int workerCount) {
        this.workerCount = workerCount;
    }

    public void event(Class<? extends Event> eventClass) {
        handledEvents.add(eventClass);
    }

    public void events(Iterable<Class<? extends Event>> eventClasses) {
        eventClasses.forEach(e -> {
            handledEvents.add(e);
        });
    }

    public Collection<Class<? extends Event>> getHandledEvents() {
        return handledEvents;
    }

    public EventWorkerPool build() {
        return new EventWorkerPool(workerCount, handledEvents);
    }
}
