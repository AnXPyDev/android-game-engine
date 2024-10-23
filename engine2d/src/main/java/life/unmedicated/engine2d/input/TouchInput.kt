package life.unmedicated.engine2d.input

import android.annotation.SuppressLint
import android.text.method.Touch
import android.view.MotionEvent
import android.view.View
import life.unmedicated.engine2d.core.Engine
import life.unmedicated.engine2d.eventcore.Event
import life.unmedicated.engine2d.eventcore.EventService
import life.unmedicated.engine2d.util.Vec
import java.util.concurrent.Executors

class TouchEvent(override val identifier: Event.ID, val pos: Vec) : Event {
    companion object {
        val DOWN = Event.ID()
        val UP = Event.ID()
        val MOVE = Event.ID()

        fun registerEvents(eventService: EventService) {
            eventService.registerEvents(DOWN, UP, MOVE)
        }
    }

    private var isValid = true
    override val valid get() = isValid

    fun capture() {
        isValid = false
    }
}

class TouchInput(private val engine: Engine) {
    private val executorService = Executors.newSingleThreadExecutor()

    init {
        TouchEvent.registerEvents(engine.eventService)
        engine.displayManager.displayView.setOnTouchListener(this::onTouch)
    }

    fun onTouch(view: View, event: MotionEvent): Boolean {
        val pos = Vec(event.x.toDouble(), event.y.toDouble())
        when(event.action) {
            MotionEvent.ACTION_DOWN ->
                executorService.execute(engine.eventService.defer(
                    TouchEvent(TouchEvent.DOWN, pos)
                ))
            MotionEvent.ACTION_UP ->
                executorService.execute(engine.eventService.defer(
                    TouchEvent(TouchEvent.UP, pos)
                ))
            MotionEvent.ACTION_MOVE ->
                executorService.execute(engine.eventService.defer(
                    TouchEvent(TouchEvent.MOVE, pos)
                ))
            else -> return false
        }
        return true
    }
}