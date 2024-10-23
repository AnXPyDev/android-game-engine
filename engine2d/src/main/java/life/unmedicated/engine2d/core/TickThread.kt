package life.unmedicated.engine2d.core

import android.util.Log
import kotlin.math.min

class TickThread(private val engineContext: EngineContext) : Thread() {
    override fun run() {
        try {
            var lastTime: Long = System.nanoTime()
            while (!isInterrupted) {
                val now = System.nanoTime()
                val dt: Double = min((now - lastTime).toDouble() / 1E9, 0.1)
                engineContext.eventService.handle(TickEvent(dt))
                //Log.i("TICK", "tick ${dt}")
                sleep(6)
                lastTime = now
            }
        } catch (_: InterruptedException) {}
    }
}