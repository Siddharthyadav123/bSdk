package com.morfeus.android.mfsdk.messenger.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * BlockingQueue contains unique messages.
 */
public class UniqueBlockingQueue<T> extends LinkedBlockingDeque<T> {
    private Set<T> set = new HashSet<T>();

    /**
     * Add element, which is not in queue.
     *
     * @param t add element
     * @return true if element is inserted otherwise false
     */
    @Override
    public synchronized boolean add(T t) {
        if (set.contains(t)) {
            return false;
        } else {
            set.add(t);
            return super.add(t);
        }
    }

    @Override
    public T take() throws InterruptedException {
        T t = super.take();
        set.remove(t);
        return t;
    }
}
