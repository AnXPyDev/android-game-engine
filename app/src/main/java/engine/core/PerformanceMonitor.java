package engine.core;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.concurrent.atomic.AtomicInteger;

import engine.graphics.DrawCall;
import engine.graphics.DrawQueue;

public class PerformanceMonitor {
    public static class Draw implements DrawCall {
        public static Paint textPaint = new Paint();
        public static Paint bgPaint = new Paint();

        public static float height = 50;
        public static float padding = 10;
        public static float fontSize = 30;

        public static void initStatic() {
            textPaint.setColor(Color.YELLOW);
            textPaint.setTextSize(fontSize);
            bgPaint.setColor(Color.BLACK);
        }

        private float ox, oy;
        private String[] lines;

        public Draw(String[] lines, float ox, float oy) {
            this.lines = lines;
            this.ox = ox;
            this.oy = oy;
        }

        @Override
        public void draw(Canvas canvas) {
            float y = oy;
            for (String text : lines) {
                canvas.drawRect(ox, y, ox + text.length() * fontSize * (float)0.75, y + height, bgPaint);
                canvas.drawText(text, ox + padding, y + height - padding, textPaint);
                y += height + padding;
            }
        }

        @Override
        public int getIndex() {
            return Integer.MAX_VALUE;
        }
    }

    public static int nanosToMicros(long nanos) {
        return (int)(nanos / 1000);
    }

    // in microseconds
    public AtomicInteger tickTime = new AtomicInteger(0);
    public AtomicInteger tickDelta = new AtomicInteger(0);

    // in microseconds
    public AtomicInteger drawTime = new AtomicInteger(0);
    public AtomicInteger drawDelta = new AtomicInteger(0);
    public AtomicInteger drawIdle = new AtomicInteger(0);

    public AtomicInteger droppedFrames = new AtomicInteger(0);
    public AtomicInteger missedFrames = new AtomicInteger(0);

    public DrawCall draw() {
        String[] lines = new String[] {
            "TT: " + tickTime , "TD: " + tickDelta,
            "DT: " + drawTime , "DD: " + drawDelta,
            "DI: " + drawIdle , "DF: " + droppedFrames,
            "MF: " + missedFrames
        };

        return new Draw(lines, 20, 20);
    }
}
