package engine.event;

public interface EventHandler {
    void submitEvent(Event event);
    EventListener addListener(EventListener listener);
    void removeListener(EventListener listener);
    EventListener addListener(Class<? extends Event> eventClass, EventListener listener);
    void removeListener(Class<? extends Event> eventClass, EventListener listener);
}
