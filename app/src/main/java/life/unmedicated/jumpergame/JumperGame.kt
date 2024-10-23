package life.unmedicated.jumpergame

import android.graphics.Color
import android.graphics.Paint
import life.unmedicated.engine2d.core.Engine
import life.unmedicated.engine2d.core.EngineContext
import life.unmedicated.engine2d.core.TickEvent
import life.unmedicated.engine2d.eventcore.Event
import life.unmedicated.engine2d.eventcore.EventHandler
import life.unmedicated.engine2d.eventcore.EventListenerDirectory
import life.unmedicated.engine2d.graphics.DrawPaintCall
import life.unmedicated.engine2d.graphics.DrawQueue
import life.unmedicated.engine2d.graphics.IndexedDrawCall
import life.unmedicated.engine2d.graphics.SortedDrawQueue
import life.unmedicated.engine2d.util.SEL
import life.unmedicated.engine2d.util.SignalEvent
import life.unmedicated.jumpergame.entity.JumperEntity
import life.unmedicated.engine2d.util.Box
import life.unmedicated.jumpergame.entity.Director
import life.unmedicated.engine2d.util.RNG
import life.unmedicated.engine2d.util.Vec

class GameTickEvent(val dt: Double) : Event {
    override val identifier get() = JumperGame.TICK
}

class GameDrawEvent(val queue: DrawQueue) : Event {
    override val identifier get() = JumperGame.DRAW
}

class JumperGame(private val context: EngineContext)  {
    companion object {
        val TICK = Event.ID()
        val DRAW = Event.ID()
        val COLLISION = Event.ID()
        val CAMERAMOVE = Event.ID()

        private val BACKGROUND = Paint().apply { color = Color.WHITE }
    }

    val rng = RNG()

    init {
        arrayOf(TICK, DRAW, COLLISION, CAMERAMOVE)
            .forEach(context.eventService::registerEvent)
    }

    lateinit var display: Box

    var ld: EventListenerDirectory = context.eventService
    var eh: EventHandler = context.eventService

    val tickListener = SEL(Engine.TICK, Int.MIN_VALUE, this::tick)

    val director = Director(this)


    init {
        ld.listen(SEL(Engine.INIT, 0, this::init))
        ld.listen(SEL(Engine.SHUTDOWN, 0, this::shutdown))
    }


    fun init(event: SignalEvent) {

        val displaySize = Vec(
            context.displayManager.displayView.width.toDouble(),
            context.displayManager.displayView.height.toDouble()
        )

        display = Box(displaySize / 2.0, displaySize)

        val ald = context.eventService.atomic()
        ld = ald

        ld.listen(tickListener)

        director.populateInitial()

        for (i in 1..1) {
            JumperEntity(this)
        }

        ld = context.eventService

        ald.defer()
    }

    fun tick(tickEvent: TickEvent) {
        val drawQueue = SortedDrawQueue()

        drawQueue.push(IndexedDrawCall(DrawPaintCall(BACKGROUND), Integer.MIN_VALUE))

        eh.handle(GameTickEvent(tickEvent.dt))
        eh.handle(GameDrawEvent(drawQueue))

        context.renderThread.submitFrame(drawQueue)
    }

    fun shutdown(event: SignalEvent) {
    }
}