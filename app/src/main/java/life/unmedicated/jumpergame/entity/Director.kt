package life.unmedicated.jumpergame.entity

import life.unmedicated.engine2d.util.Box
import life.unmedicated.engine2d.util.SEL
import life.unmedicated.engine2d.util.Vec
import life.unmedicated.jumpergame.JumperGame
import life.unmedicated.jumpergame.event.CameraMoveEvent

class Director(val game: JumperGame) {
    companion object {
        val PERDISPLAY = 10
        val PLATFORMSIZE = Vec(200.0, 40.0)
    }

    var offset: Double = 0.0

    val cameraListener = SEL(JumperGame.CAMERAMOVE, Int.MAX_VALUE, this::cameraMove)

    init {
        game.ld.listen(cameraListener)
    }

    fun populate(area: Box) {
        for (i in 1..PERDISPLAY) {
            PlatformEntity(game, Box(area.randomPoint(game.rng), PLATFORMSIZE))
        }
    }

    fun populateInitial() {
        populate(game.display)
        populate(game.display - game.display.size.Y)
    }

    fun cameraMove(event: CameraMoveEvent) {
        offset -= event.offset.y

        if (offset > game.display.height) {
            offset -= game.display.height
            populate(game.display - game.display.size.Y)
        }

    }
}