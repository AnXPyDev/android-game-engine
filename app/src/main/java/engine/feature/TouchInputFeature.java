package engine.feature;

import android.view.MotionEvent;
import android.view.View;

import engine.core.Engine;
import engine.core.EngineContext;
import engine.core.Feature;
import engine.core.EngineGame;
import engine.event.EventHandler;
import engine.event.TouchEvent;
import engine.util.Vector;

public class TouchInputFeature implements Feature {
    public static class TouchListener implements View.OnTouchListener {
        private final EventHandler eventHandler;

        public TouchListener(EventHandler eventHandler) {
            this.eventHandler = eventHandler;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                eventHandler.submitEvent(new TouchEvent(new Vector((double)motionEvent.getX(), (double)motionEvent.getY())));
            }
            return true;
        }
    }

    @Override
    public void register(EngineContext engine) {
        engine.getEventServiceBuilder().createPool("FEATURE_TOUCH_INPUT", 2).event(TouchEvent.class);
    }

    @Override
    public void setup(EngineContext engine) {
        engine.getDisplayManager().getDisplayView().setOnTouchListener(new TouchListener(engine.getEventHandler(TouchEvent.class)));
    }
}
