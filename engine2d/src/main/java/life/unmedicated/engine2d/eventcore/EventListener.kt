package life.unmedicated.engine2d.eventcore

interface EventListener {
    val eventIdentifier: Event.ID
    val index: Int
    val valid: Boolean

    fun handle(event: Event)
}