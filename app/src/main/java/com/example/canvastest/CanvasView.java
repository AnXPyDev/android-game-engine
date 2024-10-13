package com.example.canvastest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintSet;

import java.util.ArrayList;

public class CanvasView extends View {
    static class Point {
        float x, y;
        Point(float x, float y) {
            this.x = x;
            this.y = y;
        }
    }

    static class Line {
        ArrayList<Point> points = new ArrayList<>();
        void draw(Canvas canvas, Paint paint) {

            Point lastPoint = points.get(0);
            for (int i = 1; i < points.size(); i++) {
                Point point = points.get(i);
                canvas.drawLine(lastPoint.x, lastPoint.y, point.x, point.y, paint);
                lastPoint = point;
            }
        }

        void push(Point point) {
            points.add(point);
        }
    }

    Paint bgPaint = new Paint();
    Paint linePaint = new Paint();

    GameManager gameManager;

    public CanvasView(Context context, GameManager gameManager) {
        super(context);
        bgPaint.setColor(Color.WHITE);
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(10);
        this.gameManager = gameManager;
    }

    ArrayList<Line> lines = new ArrayList<>();
    Line currentLine;


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            gameManager.postEvent(new TouchEvent(new Vector((double) event.getX(), (double) event.getY())));
        }

        /*
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            currentLine = new Line();
            lines.add(currentLine);
            currentLine.push(new Point(event.getX(), event.getY()));
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            currentLine.push(new Point(event.getX(), event.getY()));
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            currentLine.push(new Point(event.getX(), event.getY()));
        }

        invalidate();
         */

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /*

        int w = canvas.getWidth();
        int h = canvas.getHeight();

        canvas.drawPaint(bgPaint);

        for (Line line : lines) {
            line.draw(canvas, linePaint);
        }
         */

        gameManager.draw(canvas);

    }
}
