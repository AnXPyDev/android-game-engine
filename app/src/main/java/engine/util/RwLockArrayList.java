package engine.util;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class RwLockArrayList<E> extends ArrayList<E> {
    public final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
}
