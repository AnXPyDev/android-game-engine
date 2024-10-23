package life.unmedicated.engine2d.eventcore

import life.unmedicated.engine2d.util.ReadWriteRef
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EventService : EventHandler, EventListenerDirectory {
    protected val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    protected val listenerDirectory = ReadWriteRef(HashMap<Event.ID, ReadWriteRef<ArrayList<EventListener>>>())

    private inner class AEO : AtomicEventOperation {
        val addList = ArrayList<EventListener>()
        val removeList = ArrayList<EventListener>()
        override fun listen(listener: EventListener) { addList.add(listener) }
        override fun listenAll(listeners: Collection<EventListener>) { addList.addAll(listeners) }

        override fun remove(listener: EventListener) { removeList.add(listener) }
        override fun removeAll(listeners: Collection<EventListener>) { removeList.addAll(listeners) }

        private fun run() {
            listenerDirectory.write {
                if (addList.isNotEmpty()) {
                    this@EventService.syncListenAll(addList)
                }

                if (removeList.isNotEmpty()) {
                    this@EventService.syncRemoveAll(removeList)
                }
            }
        }

        override fun defer() {
            executorService.execute(this::run)
        }

    }

    fun atomic(): AtomicEventOperation = AEO()

    fun registerEvent(identifier: Event.ID) {
        listenerDirectory.write {
            it.put(identifier, ReadWriteRef(ArrayList()))
        }
    }

    fun registerEvents(vararg identifiers: Event.ID) {
        listenerDirectory.write {
            directory ->
            identifiers.forEach {
                directory.put(it, ReadWriteRef(ArrayList()))
            }
        }
    }

    private fun syncListen(listener: EventListener) {
        listenerDirectory.read {
            directory ->
            directory[listener.eventIdentifier]!!.write {
                list ->
                list.add(listener)
                list.sortBy(EventListener::index)
            }
        }
    }

    private fun syncListenAll(listeners: Collection<EventListener>) {
        listenerDirectory.read {
            directory ->
            listeners.groupBy(EventListener::eventIdentifier).forEach {
                group ->
                directory[group.key]!!.write {
                    list ->
                    list.addAll(group.value)
                    list.sortBy(EventListener::index)
                }
            }
        }
    }

    private fun syncRemove(listener: EventListener) {
        listenerDirectory.read {
            directory ->
            directory[listener.eventIdentifier]!!.write {
                list ->
                list.remove(listener)
            }
        }
    }

    private fun syncRemoveAll(listeners: Collection<EventListener>) {
        listenerDirectory.read {
            directory ->
            listeners.groupBy(EventListener::eventIdentifier).forEach {
                group ->
                directory[group.key]!!.write {
                    list -> list.removeAll(group.value)
                }
            }
        }
    }

    override fun handle(event: Event) {
        listenerDirectory.read {
            val listeners = it[event.identifier]!!
            listeners.read {
                list ->
                for (listener in list) {
                    if (!listener.valid) {
                        continue
                    }
                    if (!event.valid) {
                        break
                    }
                    listener.handle(event)
                }
            }
        }
    }

    override fun defer(event: Event): () -> Unit = { handle(event) }

    override fun listen(listener: EventListener) {
        executorService.execute { syncListen(listener) }
    }

    override fun listenAll(listeners: Collection<EventListener>) {
        executorService.execute { syncListenAll(listeners) }
    }

    override fun remove(listener: EventListener) {
        executorService.execute { syncRemove(listener) }
    }

    override fun removeAll(listeners: Collection<EventListener>) {
        executorService.execute { syncRemoveAll(listeners) }
    }
}