package life.unmedicated.engine2d.graphics

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF

class DrawRectCall(private val rect: RectF, private val paint: Paint) : DrawCall {
    override fun draw(canvas: Canvas) {
        canvas.drawRect(rect, paint)
    }
}