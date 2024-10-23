package life.unmedicated.engine2d.graphics

import android.graphics.Canvas

class SortedDrawQueue : DrawQueue, DrawCall {
    val calls = ArrayList<DrawCall>()

    override fun push(drawCall: DrawCall) {
        calls.add(drawCall)
    }

    override fun draw(canvas: Canvas) {
        calls.sortBy(DrawCall::index)
        for (call in calls) {
            call.draw(canvas)
        }
    }
}