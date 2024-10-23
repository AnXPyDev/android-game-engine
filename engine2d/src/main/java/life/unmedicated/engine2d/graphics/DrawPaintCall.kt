package life.unmedicated.engine2d.graphics

import android.graphics.Canvas
import android.graphics.Paint

class DrawPaintCall(private val paint: Paint) : DrawCall {
    override fun draw(canvas: Canvas) {
        canvas.drawPaint(paint)
    }
}