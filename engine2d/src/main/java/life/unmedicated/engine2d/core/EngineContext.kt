package life.unmedicated.engine2d.core

import life.unmedicated.engine2d.eventcore.EventService

interface EngineContext {
    val displayManager: DisplayManager
    val eventService: EventService
    val renderThread: RenderThread
}