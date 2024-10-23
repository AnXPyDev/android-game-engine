package life.unmedicated.jumpergame.entity

import android.graphics.Color
import android.graphics.Paint
import life.unmedicated.engine2d.graphics.DrawRectCall
import life.unmedicated.engine2d.graphics.IndexedDrawCall
import life.unmedicated.engine2d.util.SEL
import life.unmedicated.jumpergame.GameDrawEvent
import life.unmedicated.jumpergame.JumperGame
import life.unmedicated.jumpergame.event.CameraMoveEvent
import life.unmedicated.jumpergame.event.CollisionEvent
import life.unmedicated.engine2d.util.Box

class PlatformEntity(val game: JumperGame, var box: Box) {
    companion object {
        val COLOR = Paint().apply { color = Color.GRAY }
    }

    val collisionListener = SEL(JumperGame.COLLISION, 0, this::collide)
    val drawListener = SEL(JumperGame.DRAW, 0, this::draw)
    val cameraListener = SEL(JumperGame.CAMERAMOVE, 0, this::cameraMove)

    init {
        game.ld.listenAll(collisionListener, drawListener, cameraListener)
    }

    fun collide(event: CollisionEvent) {
        if (box.intersect(event.target)) {
            event.submit(box)
        }
    }

    fun draw(event: GameDrawEvent) {
        event.queue.push(IndexedDrawCall(
            DrawRectCall(box.toRectF(), COLOR), 0
        ))
    }

    fun cameraMove(event: CameraMoveEvent) {
        box -= event.offset
        if (game.display.s < box.n) {
            despawn()
        }
    }

    fun despawn() {
        SEL.invalidateAndRemove(game.ld, drawListener, collisionListener, cameraListener)
    }
}