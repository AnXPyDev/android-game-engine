package engine.event;

public interface Event {
    default void onComplete() {}
}
