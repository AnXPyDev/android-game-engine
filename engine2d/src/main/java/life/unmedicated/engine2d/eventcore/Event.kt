package life.unmedicated.engine2d.eventcore

interface Event {
    class ID

    val identifier: ID
    val valid: Boolean
        get() = true
    fun onProcessing() {}
    fun onComplete() {}
}