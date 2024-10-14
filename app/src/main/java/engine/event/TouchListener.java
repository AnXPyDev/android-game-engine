package engine.event;

import android.view.MotionEvent;
import android.view.View;

import engine.Game;
import engine.Vector;

public class TouchListener implements View.OnTouchListener {
    Game game;

    public TouchListener(Game game) {
        this.game = game;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            game.postEvent(new TouchEvent(new Vector((double)motionEvent.getX(), (double)motionEvent.getY())));
        }
        return true;
    }
}
