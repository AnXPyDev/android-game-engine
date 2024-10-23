package life.unmedicated.engine2d.eventcore

interface EventListenerDirectory {
    fun listen(listener: EventListener)
    fun listenAll(listeners: Collection<EventListener>)
    fun remove(listener: EventListener)
    fun removeAll(listeners: Collection<EventListener>)

    fun listenAll(vararg listeners: EventListener) { listenAll(listeners.toList()) }
    fun removeAll(vararg listeners: EventListener) { removeAll(listeners.toList()) }
}