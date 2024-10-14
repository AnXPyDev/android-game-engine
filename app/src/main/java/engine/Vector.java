package engine;

import androidx.annotation.NonNull;

public class Vector {
    public double x, y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(double l) {
        x = l;
        y = l;
    }

    public Vector() {
        x = 0;
        y = 0;
    }

    public Vector copy() {
        return new Vector(x, y);
    }

    public void replace(Vector v) {
        x = v.x;
        y = v.y;
    }

    public void add(Vector v) {
        x += v.x;
        y += v.y;
    }

    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void add(double s) {
        x += s;
        y += s;
    }

    public void scale(Vector v) {
        x *= v.x;
        y *= v.y;
    }

    public void scale(double x, double y) {
        this.x *= x;
        this.y *= y;
    }

    public void scale(double s) {
        x *= s;
        y *= s;
    }


    public Vector sum(Vector v) {
        return new Vector(x + v.x, y + v.y);
    }

    public Vector sum(double x, double y) {
        return new Vector(this.x + x, this.y + y);
    }

    public Vector sum(double s) {
        return new Vector(x + s, y + s);
    }

    public Vector product(Vector v) {
        return new Vector(x * v.x, y * v.y);
    }

    public Vector product(double x, double y) {
        return new Vector(this.x * x, this.y * y);
    }

    public Vector product(double s) {
        return new Vector(x * s, y * s);
    }

    @Override
    public String toString() {
        return "Vector{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
