package engine.event;

import java.util.Comparator;

import engine.util.RwLockArrayList;

public class SortedEventListenerList extends RwLockArrayList<EventListener> {
    @Override
    public boolean add(EventListener eventListener) {
        boolean result = super.add(eventListener);
        sort(Comparator.comparingInt(EventListener::getPriority));
        return result;
    }
}
