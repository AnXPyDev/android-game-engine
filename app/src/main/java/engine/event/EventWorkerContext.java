package engine.event;

public interface EventWorkerContext {
    void handleEvent(Event event);
    void handleTask(Runnable task);
    Event pollEvent();
    Runnable pollTask();
    Object getNotifyHandler();
}
