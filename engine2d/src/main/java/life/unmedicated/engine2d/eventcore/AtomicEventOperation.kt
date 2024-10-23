package life.unmedicated.engine2d.eventcore

interface AtomicEventOperation : EventListenerDirectory {
    override fun listen(listener: EventListener)
    override fun listenAll(listeners: Collection<EventListener>)
    override fun remove(listener: EventListener)
    override fun removeAll(listeners: Collection<EventListener>)
    fun defer()
}