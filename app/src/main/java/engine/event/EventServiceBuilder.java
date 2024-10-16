package engine.event;

import java.util.ArrayList;
import java.util.HashMap;

public class EventServiceBuilder {
    private final HashMap<String, EventWorkerPoolBuilder> poolBuilders = new HashMap<>();

    public EventWorkerPoolBuilder getPool(String name) {
        EventWorkerPoolBuilder pool = poolBuilders.get(name);
        if (pool == null) {
            throw new RuntimeException(String.format("pool %s does not exist", name));
        }
        return pool;
    }

    public EventWorkerPoolBuilder createPool(String name, int workerCount) {
        EventWorkerPoolBuilder pool = poolBuilders.get(name);
        if (pool !=  null) {
            throw new RuntimeException(String.format("pool %s already exists", name));
        }

        pool = new EventWorkerPoolBuilder(workerCount);
        poolBuilders.put(name, pool);

        return pool;
    }

    public EventService build() {
        ArrayList<EventWorkerPool> pools = new ArrayList<>();
        HashMap<Class<? extends Event>, EventWorkerPool> poolDirectory = new HashMap<>();

        for (EventWorkerPoolBuilder poolBuilder : poolBuilders.values()) {
            EventWorkerPool pool = poolBuilder.build();
            for (Class<? extends Event> eventClass : poolBuilder.getHandledEvents()) {
                if (poolDirectory.containsKey(eventClass)) {
                    throw new RuntimeException(String.format("event %s registered twice", eventClass));
                }

                poolDirectory.put(eventClass, pool);
                pools.add(pool);
            }
        }

        return new EventService(pools, poolDirectory);
    }

}
