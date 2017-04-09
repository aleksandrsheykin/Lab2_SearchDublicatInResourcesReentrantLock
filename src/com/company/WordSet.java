package com.company;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by admin on 09.04.2017.
 */
public class WordSet {
    public Set<String> wordSet = new HashSet<>();
    public AtomicBoolean flStop = new AtomicBoolean(false);
    public ReentrantLock locker = new ReentrantLock();
}
