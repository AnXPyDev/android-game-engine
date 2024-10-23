package life.unmedicated.engine2d.core

import android.content.Context
import android.view.SurfaceView
import life.unmedicated.engine2d.graphics.DrawCall

class DisplayManager(context: Context) {
    val displayView = SurfaceView(context)

    fun renderFrame(frame: DrawCall) {
        displayView.apply {
            holder.lockHardwareCanvas().let {
                if (it == null) {
                    return
                }
                frame.draw(it)
                holder.unlockCanvasAndPost(it)
            }
        }

    }
}