package engine.event;

public interface EventListener {
    void handle(Event event);
    default Class<? extends Event> getEventClass() {
        throw new UnsupportedOperationException();
    }
    default int getPriority() {
        return 0;
    }
}
