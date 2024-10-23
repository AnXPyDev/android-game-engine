package life.unmedicated.engine2d.util

import java.util.concurrent.locks.ReentrantReadWriteLock
import kotlin.concurrent.read
import kotlin.concurrent.write

class ReadWriteRef<T>(val ref: T) {
    val lock = ReentrantReadWriteLock()

    fun <R> read(action: (ref: T) -> R) {
        return lock.read { action(ref) }
    }

    fun <R> write(action: (ref: T) -> R) {
        return lock.write { action(ref) }
    }
}