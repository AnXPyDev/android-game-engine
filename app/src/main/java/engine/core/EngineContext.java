package engine.core;

import java.util.concurrent.atomic.AtomicReference;

import engine.event.Event;
import engine.event.EventHandler;
import engine.event.EventService;
import engine.event.EventServiceBuilder;
import engine.graphics.DrawCall;

public interface EngineContext {
    AtomicReference<DrawCall> getFrameHolder();

    void submitFrame(DrawCall call);

    DisplayManager getDisplayManager();
    PerformanceMonitor getPerformanceMonitor();
    EngineGame getGame();
    EventService getEventService();
    EventServiceBuilder getEventServiceBuilder();

    EventHandler getEventHandler(Class<? extends Event> eventClass);
    EventHandler getEventHandler();
}
