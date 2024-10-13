package com.example.canvastest;

import android.graphics.RectF;

import androidx.annotation.NonNull;

public class Vector {
    double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double x) {
        this(x, x);
    }

    public Vector() {
        this(0, 0);
    }

    public Vector(Vector other) {
        this.x = other.x;
        this.y = other.y;
    }

    public Vector copy() {
        return new Vector(this);
    }

    public RectF crect(Vector size) {
        return new RectF(
                (float)(x - size.x / 2),
                (float)(y - size.y / 2),
                (float)(x + size.x / 2),
                (float)(y + size.y / 2)
        );
    }

    public boolean pointInsideCrect(Vector size, Vector point) {
        return (point.x >= x - size.x / 2) &&
                (point.x <= x + size.x / 2) &&
                (point.y >= y - size.y / 2) &&
                (point.y <= y + size.y / 2)
                ;
    }

    public Vector add(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    public Vector scale(Vector v) {
        return new Vector(x * v.x, y * v.y);
    }

    public Vector lcor(Vector v) {
        return new Vector(x - v.x / 2, y - v.y / 2);
    }

    public Vector rcor(Vector v) {
        return new Vector(x + v.x / 2, y + v.y / 2);
    }

    @NonNull
    @Override
    public String toString() {
        return String.format("Vector(%f, %f)", x, y);
    }
}
