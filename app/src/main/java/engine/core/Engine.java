package engine.core;

import android.app.Activity;
import android.content.Context;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import engine.event.Event;
import engine.event.EventHandler;
import engine.event.EventService;
import engine.event.EventServiceBuilder;
import engine.event.TickEvent;
import engine.event.TouchEvent;
import engine.graphics.CompositeDrawCall;
import engine.graphics.DrawCall;

public final class Engine implements EngineContext {
    private static boolean staticInitialized = false;

    public static void initStatic() {
        staticInitialized = true;
        PerformanceMonitor.Draw.initStatic();
    }

    private final DisplayManager displayManager;
    private final LogicThread logicThread = new LogicThread(this);
    private final RenderThread renderThread = new RenderThread(this);
    private final PerformanceMonitor performanceMonitor = new PerformanceMonitor();
    private final AtomicReference<DrawCall> readyFrame = new AtomicReference<>();

    private final ArrayList<Feature> features = new ArrayList<>();

    private EventServiceBuilder eventServiceBuilder = new EventServiceBuilder();

    private EventService eventService;

    private boolean performanceOverlayEnabled = false;

    private EngineGame game;

    private EventHandler tickHandler;

    public Engine(Context context) {

        eventServiceBuilder.createPool("ENGINE_TICK", 1)
                .event(TickEvent.class);

        if (!staticInitialized) {
            throw new RuntimeException("Engine not static initialized");
        }

        displayManager = new DisplayManager(context);
    }

    private void start() {
        displayManager.init();
        game.init(this);
        eventService.start();
        logicThread.start();
        renderThread.start();
    }

    public void boot(Activity activity, EngineGame game) {
        eventService = eventServiceBuilder.build();
        eventServiceBuilder = null;

        for (Feature feature : features) {
            feature.setup(this);
        }

        tickHandler = this.eventService.getEventHandler(TickEvent.class);

        this.game = game;

        activity.setContentView(displayManager.getDisplayView());
        displayManager.getDisplayView().post(this::start);
    }

    public void enableFeature(Class<? extends Feature> featureClass) {
        try {
            Feature feature = featureClass.newInstance();
            feature.register(this);
            features.add(feature);
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEnabledPerformanceOverlay(boolean enabled) {
        performanceOverlayEnabled = enabled;
    }

    @Override
    public AtomicReference<DrawCall> getFrameHolder() {
        return readyFrame;
    }

    @Override
    public void submitFrame(DrawCall frame) {
        if (performanceOverlayEnabled) {
            frame = new CompositeDrawCall(new DrawCall[] {frame, performanceMonitor.draw()});
        }

        renderThread.submitFrame(frame);
    }

    public void pause() {
        logicThread.interrupt();
    }

    public void resume() {
        logicThread.start();
    }

    @Override
    public DisplayManager getDisplayManager() {
        return displayManager;
    }

    @Override
    public PerformanceMonitor getPerformanceMonitor() {
        return performanceMonitor;
    }

    @Override
    public EngineGame getGame() {
        return game;
    }

    @Override
    public EventService getEventService() {
        return eventService;
    }

    @Override
    public EventHandler getEventHandler(Class<? extends Event> eventClass) {
        return eventService.getEventHandler(eventClass);
    }

    @Override
    public EventHandler getEventHandler() {
        return eventService;
    }

    public EventServiceBuilder getEventServiceBuilder() {
        return eventServiceBuilder;
    }
}
