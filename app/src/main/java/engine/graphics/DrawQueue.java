package engine.graphics;

import android.graphics.Canvas;

import java.util.ArrayList;

public class DrawQueue implements DrawCall {
    public static final int LAYER_COUNT = 12;
    public static final int LAYER_BG = 0;
    public static final int LYAER_UI = 11;
    public static final int LAYER_DEFAULT = 6;

    ArrayList<DrawCall>[] layers;

    public DrawQueue() {
        layers = new ArrayList[LAYER_COUNT];
    }

    public void push(DrawCall call, int layer) {
        if (layer < 0 || layer >= LAYER_COUNT) {
            throw new RuntimeException(String.format("LAYER %s OUT OF RANGE (%s-%s)", layer, 0, LAYER_COUNT - 1));
        }
        if (layers[layer] == null) {
            layers[layer] = new ArrayList<DrawCall>();
        }

        layers[layer].add(call);
    }

    public void draw(Canvas canvas) {
        for (ArrayList<DrawCall> layer : layers) {
            if (layer == null) {
                continue;
            }
            for (DrawCall call : layer) {
                call.draw(canvas);
            }
        }
    }

}
