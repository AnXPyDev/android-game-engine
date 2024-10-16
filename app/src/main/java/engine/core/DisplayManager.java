package engine.core;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import engine.graphics.DrawCall;
import engine.util.Vector;

public class DisplayManager {
    SurfaceView displayView;
    Vector displaySize;

    public DisplayManager(Context context) {
        displayView = new SurfaceView(context);
    }

    public void init() {
        displaySize = new Vector(displayView.getWidth(), displayView.getHeight());
    }

    public void render(DrawCall drawCall) {
        SurfaceHolder surfaceHolder = displayView.getHolder();
        Canvas canvas = surfaceHolder.lockHardwareCanvas();
        drawCall.draw(canvas);
        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    public View getDisplayView() {
        return displayView;
    }

    public Vector getDisplaySize() {
        return displaySize.copy();
    }
}
