package life.unmedicated.jumpergame.event

import life.unmedicated.engine2d.eventcore.Event
import life.unmedicated.jumpergame.JumperGame
import life.unmedicated.engine2d.util.Box

class CollisionEvent(val target: Box) : Event {
    override val identifier get() = JumperGame.COLLISION

    val collisions = ArrayList<Box>()

    fun submit(box: Box) { collisions.add(box) }
}