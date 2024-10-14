package engine;

import android.graphics.RectF;

import java.util.Random;


public class Box {
    public static Vector lcor(Vector center, Vector size) {
        return new Vector(center.x - size.x / 2, center.y - size.y / 2);
    }

    public static Vector rcor(Vector center, Vector size) {
        return new Vector(center.x + size.x / 2, center.y + size.y / 2);
    }

    public static double left(Vector center, Vector size) {
        return center.x - size.x / 2;
    }

    public static double right(Vector center, Vector size) {
        return center.x + size.x / 2;
    }

    public static double top(Vector center, Vector size) {
        return center.y - size.y / 2;
    }

    public static double bottom(Vector center, Vector size) {
        return center.y + size.y / 2;
    }

    public static boolean collides(Vector center, Vector size, Vector point) {
        double lx = center.x - size.x / 2;
        double ly = center.y - size.y / 2;
        double rx = center.x + size.x / 2;
        double ry = center.y + size.y / 2;

        return (point.x >= lx && point.y >= ly && point.x <= rx && point.y <= ry);
    }

    public static RectF toRectF(Vector center, Vector size) {
        double lx = center.x - size.x / 2;
        double ly = center.y - size.y / 2;
        double rx = center.x + size.x / 2;
        double ry = center.y + size.y / 2;

        return new RectF((float)lx, (float)ly, (float)rx, (float)ry);
    }

    public static Vector randomPos(Vector center, Vector size, Random random) {
        double lx = center.x - size.x / 2;
        double ly = center.y - size.y / 2;

        return new Vector(
                lx + size.x * random.nextDouble(),
                ly + size.y * random.nextDouble()
        );
    }

}
