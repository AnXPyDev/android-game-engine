package life.unmedicated.engine2d.util

import life.unmedicated.engine2d.eventcore.Event
import life.unmedicated.engine2d.eventcore.EventListener
import life.unmedicated.engine2d.eventcore.EventListenerDirectory

class SimpleEventListener<T : Event>(
    override val eventIdentifier: Event.ID,
    override val index: Int = 0,
    private val handler: (T) -> Unit
) : EventListener {

    companion object {
        fun invalidate(vararg listeners: SimpleEventListener<*>) {
            listeners.forEach(SimpleEventListener<*>::invalidate)
        }
        fun invalidateAndRemove(ld: EventListenerDirectory, vararg listeners: SimpleEventListener<*>) {
            invalidate(*listeners)
            ld.removeAll(*listeners)
        }
    }

    override var valid: Boolean = true
    fun invalidate() { valid = false }

    override fun handle(event: Event) {
        @Suppress("UNCHECKED_CAST")
        handler.invoke(event as T)
    }
}

typealias SEL<T> = SimpleEventListener<T>