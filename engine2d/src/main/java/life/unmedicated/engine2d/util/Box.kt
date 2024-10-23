package life.unmedicated.engine2d.util

import android.graphics.RectF
import kotlin.math.abs

data class Box(val center: Vec, val size: Vec) {
    enum class Edge { N, W, S, E, NONE }

    companion object {
        val EDGES = arrayOf(Edge.N, Edge.W, Edge.S, Edge.E)
    }

    val x get() = center.x
    val y get() = center.y
    val width get() = size.x
    val height get() = size.y

    val n = y - height / 2.0
    val w = x - width / 2.0
    val s = y + height / 2.0
    val e = x + width / 2.0

    val NW get() = Vec(w, n)
    val NE get() = Vec(e, n)
    val SW get() = Vec(w, s)
    val SE get() = Vec(e, s)

    val NC get() = Vec(x, n)
    val CW get() = Vec(w, y)
    val SC get() = Vec(x, s)
    val CE get() = Vec(e, y)

    val area get() = size.x * size.y

    fun contains(v: Vec) = w <= v.x && n <= v.y && e >= v.x && s >= v.y
    fun intersect(b: Box) = w <= b.e && n <= b.s && e >= b.w && s >= b.n

    fun toRectF(): RectF = RectF(w.toFloat(), n.toFloat(), e.toFloat(), s.toFloat())

    fun nearestEdge(other: Box): Edge {
        val distW = abs(w - other.e)
        val distS = abs(s - other.n)
        val distE = abs(e - other.w)

        var min = abs(n - other.s)
        var edge = Edge.N

        if (distW < min) {
            min = distW
            edge = Edge.W
        }
        if (distS < min) {
            min = distS
            edge = Edge.S
        }
        if (distE < min) {
            edge = Edge.E
        }

        return edge
    }

    fun randomPoint(rng: RNG) = Vec(rng.double(w, e), rng.double(n, s))

    operator fun plus(offset: Vec) = Box(center + offset, size)
    operator fun minus(offset: Vec) = Box(center - offset, size)

    fun move(newCenter: Vec) = Box(newCenter, size)

}