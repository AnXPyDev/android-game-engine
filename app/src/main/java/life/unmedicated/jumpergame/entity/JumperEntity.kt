package life.unmedicated.jumpergame.entity

import android.graphics.Color
import android.graphics.Paint
import life.unmedicated.engine2d.graphics.DrawRectCall
import life.unmedicated.engine2d.graphics.IndexedDrawCall
import life.unmedicated.engine2d.input.TouchEvent
import life.unmedicated.engine2d.util.SEL
import life.unmedicated.jumpergame.GameDrawEvent
import life.unmedicated.jumpergame.GameTickEvent
import life.unmedicated.jumpergame.JumperGame
import life.unmedicated.jumpergame.event.CameraMoveEvent
import life.unmedicated.jumpergame.event.CollisionEvent
import life.unmedicated.engine2d.util.Box
import life.unmedicated.engine2d.util.Vec
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class JumperEntity(val game: JumperGame) {
    val sync = ReentrantLock()

    companion object {
        val GRAVITY = Vec(0.0, 1500.0)
        val BOUNCE_DAMPENING = 1.0
        val JUMP_VELOCITY = -1750.0
    }

    val color = Paint().apply {
        color = Color.rgb(
            game.rng.int(128, 256),
            game.rng.int(128, 256),
            game.rng.int(128, 256)
        )
    }

    val tickListener = SEL(JumperGame.TICK, 0, this::tick)
    val drawListener = SEL(JumperGame.DRAW, 0, this::draw)

    val touchListeners = arrayOf(
        SEL(TouchEvent.DOWN, 0, this::onTouch),
        SEL(TouchEvent.UP, 0, this::onTouch),
        SEL(TouchEvent.MOVE, 0, this::onTouch)
    )

    var pos = game.display.center
    var size = Vec(150.0)

    var vel = Vec()

    val upperCameraBound = game.display.y

    init {
        val speed = game.rng.double(750.0, 1500.0)

        game.ld.listenAll(tickListener, drawListener, *touchListeners)
    }

    fun tick(event: GameTickEvent) { sync.withLock {
        vel += GRAVITY * event.dt
        val box = Box(pos, size)

        val collisionEvent = CollisionEvent(box + vel * event.dt)
        game.eh.handle(collisionEvent)

        for (otherBox in collisionEvent.collisions) {
            if (box.s < otherBox.n && vel.y > 0 && box.nearestEdge(otherBox) == Box.Edge.S)
                if (box.SE.y < otherBox.NW.y && vel.y > 0 && box.nearestEdge(otherBox) == Box.Edge.S) {
                    vel = Vec(vel.x, JUMP_VELOCITY)
                    break
                }
        }


        pos += vel * event.dt

        if (vel.x < 0 && pos.x < game.display.w) {
            pos += game.display.size.X
        }

        if (vel.x > 0 && pos.x > game.display.e) {
            pos -= game.display.size.X
        }

        if (vel.y < 0 && pos.y < upperCameraBound) {
            game.eh.handle(CameraMoveEvent(Vec(0.0, pos.y - upperCameraBound)))
            pos = pos.sety(upperCameraBound)
        }
    } }

    fun draw(event: GameDrawEvent) { sync.withLock {
        event.queue.push(IndexedDrawCall(DrawRectCall(Box(pos, size).toRectF(), color), 16))
    } }

    fun onTouch(event: TouchEvent) { sync.withLock {
        pos = pos.setx(event.pos.x)
    } }
}