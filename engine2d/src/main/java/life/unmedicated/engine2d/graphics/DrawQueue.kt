package life.unmedicated.engine2d.graphics

interface DrawQueue {
    fun push(drawCall: DrawCall)
}