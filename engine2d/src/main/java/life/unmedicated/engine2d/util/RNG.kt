package life.unmedicated.engine2d.util

import java.util.Random
import kotlin.math.PI

class RNG {
    val random = Random()

    fun int(min: Int, max: Int): Int = random.nextInt(max - min) + min
    fun double(min: Double, max: Double): Double = random.nextDouble() * (max - min) + min
    fun angle(): Double = random.nextDouble() * PI * 2.0
    fun sign(): Int = if (random.nextBoolean()) 1 else -1
}