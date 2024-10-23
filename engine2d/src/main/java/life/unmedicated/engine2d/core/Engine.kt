package life.unmedicated.engine2d.core

import android.content.Context
import android.util.Log
import life.unmedicated.engine2d.eventcore.Event
import life.unmedicated.engine2d.eventcore.EventService
import life.unmedicated.engine2d.util.SignalEvent

class Engine(private val context: Context) : EngineContext {
    companion object {
        val TICK = Event.ID()
        val INIT = Event.ID()
        val SHUTDOWN = Event.ID()
    }

    override val displayManager = DisplayManager(context)
    override val eventService = EventService()

    override val renderThread = RenderThread(this)

    private val tickThread = TickThread(this)

    init {
        eventService.registerEvents(
            INIT, SHUTDOWN, TICK
        )
    }

    fun start() {
        Log.i("ENGINE", "start")

        displayManager.displayView.run {
            post {
                tickThread.start()
                renderThread.start()
                eventService.handle(SignalEvent(INIT))
            }
        }
    }
}