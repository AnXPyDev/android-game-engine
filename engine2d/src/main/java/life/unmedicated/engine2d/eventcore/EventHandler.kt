package life.unmedicated.engine2d.eventcore

interface EventHandler {
    fun handle(event: Event)
    fun defer(event: Event): () -> Unit

}