package life.unmedicated.engine2d.graphics

import android.graphics.Canvas

interface DrawCall {
    fun draw(canvas: Canvas)
    val index: Int
        get() = throw UnsupportedOperationException()
}