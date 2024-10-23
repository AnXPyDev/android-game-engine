package life.unmedicated.engine2d.util

import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vec(val x: Double = 0.0, val y: Double = x) {
    companion object {
        val ZERO = Vec()
        val INVX = Vec(-1.0, 1.0)
        val INVY = Vec(1.0, -1.0)

        fun angle(a: Double) = Vec(cos(a), -sin(a))
        fun angle(a: Double, l: Double) = Vec(cos(a) * l, sin(a) * l)
    }

    val X get() = Vec(x, 0.0)
    val Y get() = Vec(0.0, y)

    fun setx(xv: Double) = Vec(xv, y)
    fun sety(yv: Double) = Vec(x, yv)

    operator fun unaryMinus() = Vec(-x, -y)

    operator fun plus(v: Vec) = Vec(x + v.x, y + v.y)
    operator fun minus(v: Vec) = Vec(x - v.x, y - v.y)
    operator fun times(v: Vec) = Vec(x * v.x, y * v.y)
    operator fun div(v: Vec) = Vec(x / v.x, y / v.y)

    operator fun plus(m: Double) = Vec(x + m, y + m)
    operator fun minus(m: Double) = Vec(x - m, y - m)
    operator fun times(m: Double) = Vec(x * m, y * m)
    operator fun div(m: Double) = Vec(x / m, y / m)

    val magnitude: Double = sqrt(x * x + y * y)
    val angle: Double get() = atan2(-y, x)
    val unit: Vec
        get() {
        val r = magnitude
        return Vec(x / r, y / r)
    }

    fun from(origin: Vec) = this - origin
    fun to(point: Vec) = point - this

    fun rotate(a: Double) = angle(angle + a) * magnitude
}




