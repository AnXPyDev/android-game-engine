package life.unmedicated.engine2d.graphics

import android.graphics.Canvas

class CompoundDrawCall(private val calls: Array<DrawCall>) : DrawCall {
    override fun draw(canvas: Canvas) = calls.forEach { it.draw(canvas) }
}