package life.unmedicated.engine2d.core

import life.unmedicated.engine2d.eventcore.Event

class TickEvent(val dt: Double) : Event {
    override val identifier get() = Engine.TICK

}