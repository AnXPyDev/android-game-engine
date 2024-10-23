package life.unmedicated.engine2d.graphics

import android.graphics.Canvas

class IndexedDrawCall(private val drawCall: DrawCall, override val index: Int) : DrawCall {
    override fun draw(canvas: Canvas) = drawCall.draw(canvas)
}