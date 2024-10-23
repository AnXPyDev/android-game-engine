package life.unmedicated.engine2d.core

import kotlinx.coroutines.internal.SynchronizedObject
import life.unmedicated.engine2d.graphics.DrawCall
import java.util.concurrent.CountDownLatch
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.atomic.AtomicReference
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

class RenderThread(private val engineContext: EngineContext) : Thread() {
    private val latchRef = AtomicReference<CountDownLatch>()
    private val readyFrame = AtomicReference<DrawCall>()

    fun submitFrame(frame: DrawCall) {
        readyFrame.getAndSet(frame)
        synchronized(latchRef) {
            latchRef.getAndSet(null)?.countDown()
        }
    }

    override fun run() {
        try {
            while (!isInterrupted) {
                val frame = readyFrame.getAndSet(null)
                if (frame == null) {
                    var latch: CountDownLatch
                    synchronized(latchRef) {
                        latch = CountDownLatch(1)
                        latchRef.set(latch)
                    }
                    latch.await()
                    continue
                }

                engineContext.displayManager.renderFrame(frame)
            }
        } catch (_: InterruptedException) {}
    }
}