package engine.event;

public class SimpleEventListener<E extends Event> implements EventListener {
    public interface Callback<E extends Event> {
        void handle(E event);
    }

    Class<E> eventClass;
    int priority;
    Callback<E> callback;

    public SimpleEventListener(Class<E> eventClass, int priority, Callback<E> callback) {
        this.eventClass = eventClass;
        this.priority = priority;
        this.callback = callback;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void handle(Event event) {
        callback.handle((E)event);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public Class<? extends Event> getEventClass() {
        return eventClass;
    }

    public static <E extends Event> SimpleEventListener<E> create(Class<E> eventClass, int priority, Callback<E> callback) {
        return new SimpleEventListener<E>(eventClass, priority, callback);
    };
}
