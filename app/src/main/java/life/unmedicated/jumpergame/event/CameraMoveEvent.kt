package life.unmedicated.jumpergame.event

import life.unmedicated.engine2d.eventcore.Event
import life.unmedicated.jumpergame.JumperGame
import life.unmedicated.engine2d.util.Vec

class CameraMoveEvent(val offset: Vec) : Event {
    override val identifier get() = JumperGame.CAMERAMOVE
}